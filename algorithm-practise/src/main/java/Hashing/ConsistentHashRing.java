package Hashing;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * 支持引入虚拟节点的一致性哈希环
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2023-11-22
 */
public class ConsistentHashRing {
    private static final String IPV4_REGX = "(([0,1]?\\d?\\d|2[0-4]\\d|25[0-5])\\.){3}([0,1]?\\d?\\d|2[0-4]\\d|25[0-5])";
    private static final HashFunction HASH_FUNCTION = Hashing.murmur3_32_fixed();

    private final int virtualNodeSize;

    private final SortedMap<Integer, String> virtualHashRing;

    // 构建虚拟节点哈希环，实际存储的时候用的SortedMap，首尾没有相连，记录下首尾节点的哈希值，后续让所有介于首尾之间的Key都命中首节点，即构成了逻辑上的环形空间
    private ConsistentHashRing(int virtualNodeSize, List<String> realNodeIpList) {
        this.virtualNodeSize = virtualNodeSize;
        virtualHashRing = new TreeMap<>(Comparator.comparingInt(o -> o));
        for (String ip: realNodeIpList) {
            addNode(ip);
        }
        System.out.println("init hash ring success ring=" + virtualHashRing);
    }

    public String getNodeIp(String partitionKey) {
        if (StringUtils.isBlank(partitionKey)) {
            throw new InvalidParameterException("partition cant be empty");
        }
        int keyCode = hash(partitionKey);
        if (keyCode > virtualHashRing.lastKey() || keyCode <= virtualHashRing.firstKey()) {
                // 哈希值介于首尾节点之间的Key都命中首节点
            return getNodeIpFromVNode(virtualHashRing.get(virtualHashRing.firstKey()));
        }
        // 从virtualHashRing中找出第一个节点哈希值大于等于key哈希值的节点
        Integer nodeHashCode = virtualHashRing.tailMap(keyCode).firstKey();
        return getNodeIpFromVNode(virtualHashRing.get(nodeHashCode));
    }

    private String getNodeIpFromVNode(String vNodeName) {
        return vNodeName.split("-")[0];
    }

    public void addNode(String ipv4Str) {
        for(int i = 0; i < virtualNodeSize; i++) {
            String vNodeName = ipv4Str + "-" + i;
            int vNodeHashCode = hash(vNodeName);
            virtualHashRing.put(vNodeHashCode, vNodeName);
        }
        // TODO 实现算法时没有数据迁移过程，实际应用中，这里还要触发扩容迁移数据机制，从其他节点迁移数据到新增的节点
    }

    public void removeNode(String ipv4Str) {
        for(int i = 0; i < virtualNodeSize; i++) {
            String vNodeName = ipv4Str + "-" + i;
            int vNodeHashCode = hash(vNodeName);
            virtualHashRing.remove(vNodeHashCode);
        }
        // TODO 实现算法时没有数据迁移过程，实际应用中，这里还要触发备份恢复机制，从备份节点里迁移数据到其它节点
    }

    /**
     * 哈希运算规则为MurmurHash_32算法，取值范围为[Integer.MIN_VALUE,Integer.MAX_VALUE]
     * 选取MurmurHash_32是因为该算法分布非常均匀，即使key非常接近，得出的hashCode也能差距很大。并且哈希code总体会均匀的分布到整个Integer空间
     *
     * @return Integer范围内的任意一个哈希值
     */
    private int hash(String key) {
        return HASH_FUNCTION.hashUnencodedChars(key).asInt();
    }

    public static class ConsistentHashRingBuilder {
        private int virtualNodeSize = 2;
        private List<String> realNodeIpList = Collections.emptyList();

        private ConsistentHashRingBuilder() {

        }

        public static ConsistentHashRingBuilder builder() {
            return new ConsistentHashRingBuilder();
        }

        public ConsistentHashRingBuilder virtualNodeSize(int virtualNodeSize) {
            this.virtualNodeSize = virtualNodeSize;
            return this;
        }

        public ConsistentHashRingBuilder realNodeIpList(List<String> realNodeIpList) {
            this.realNodeIpList = realNodeIpList;
            return this;
        }

        public ConsistentHashRing build() {
            if (virtualNodeSize < 2) {
                throw new InvalidParameterException("virtualNodeSize must > 1");
            }
            if (CollectionUtils.isEmpty(realNodeIpList)) {
                throw new InvalidParameterException("realNodeIpList cant be empty");
            }
            if (realNodeIpList.stream().anyMatch(ipStr -> !validIpv4(ipStr))) {
                throw new InvalidParameterException("contains invalid ip");
            }

            return new ConsistentHashRing(virtualNodeSize, realNodeIpList);
        }
    }

    private static boolean validIpv4(String ipv4) {
        return Optional.ofNullable(ipv4).orElse("").matches(IPV4_REGX);
    }

    public static void main(String[] args) {
        List<String> realNodeIpList = new ArrayList<>();
        realNodeIpList.add("192.0.0.0");
        realNodeIpList.add("192.0.0.1");
        realNodeIpList.add("192.0.0.2");
        ConsistentHashRing ring = ConsistentHashRingBuilder.builder()
                .virtualNodeSize(4)
                .realNodeIpList(realNodeIpList)
                .build();
        System.out.println("key=A should get from node:" + ring.getNodeIp("A"));
        System.out.println("key=B should get from node:" + ring.getNodeIp("B"));
        System.out.println("key=C should get from node:" + ring.getNodeIp("C"));
        System.out.println("key=D should get from node:" + ring.getNodeIp("D"));
        System.out.println("key=E should get from node:" + ring.getNodeIp("E"));
        System.out.println("key=F should get from node:" + ring.getNodeIp("F"));
        ring.removeNode("192.0.0.1");
        System.out.println("after remove node 192.0.0.1");
        System.out.println("key=A should get from node:" + ring.getNodeIp("A"));
        System.out.println("key=B should get from node:" + ring.getNodeIp("B"));
        System.out.println("key=C should get from node:" + ring.getNodeIp("C"));
        System.out.println("key=D should get from node:" + ring.getNodeIp("D"));
        System.out.println("key=E should get from node:" + ring.getNodeIp("E"));
        System.out.println("key=F should get from node:" + ring.getNodeIp("F"));
        ring.addNode("192.0.0.1");
        System.out.println("after add node 192.0.0.1");
        System.out.println("key=A should get from node:" + ring.getNodeIp("A"));
        System.out.println("key=B should get from node:" + ring.getNodeIp("B"));
        System.out.println("key=C should get from node:" + ring.getNodeIp("C"));
        System.out.println("key=D should get from node:" + ring.getNodeIp("D"));
        System.out.println("key=E should get from node:" + ring.getNodeIp("E"));
        System.out.println("key=F should get from node:" + ring.getNodeIp("F"));
    }

}
