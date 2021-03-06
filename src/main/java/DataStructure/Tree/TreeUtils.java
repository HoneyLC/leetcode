package DataStructure.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TreeUtils
 * @Description
 * @Author Administrator
 * @Date 2020/6/5 0005 11:12
 */
public class TreeUtils {
    // 入口方法
    public static List<Node> getTree(List<Node> nodeList) {
        List<Node> list = new ArrayList<Node>();
        // 遍历节点列表
        for (Node node : nodeList) {
            if (node.getParentId().equals("-1")) {
                // parentID为-1（根节点）作为入口
                node.setChildren(getChildrenNode(node.getId(), nodeList));
                list.add(node);
            }
        }
        return list;
    }
    // 获取子节点的递归方法
    private static List<Node> getChildrenNode(String id, List<Node> nodeList) {
        List<Node> list = new ArrayList<Node>();
        for (Node node : nodeList) {
            if (node.getParentId().equals(id)) {
                // 递归获取子节点
                node.setChildren(getChildrenNode(node.getId(), nodeList));
                list.add(node);
            }
        }
        return list;
    }

}
