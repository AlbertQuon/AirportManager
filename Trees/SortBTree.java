package Trees;

import StackAndQueues.Stack;

public class SortBTree<E extends Comparable<E>> {
    private SortBTreeNode root;
    private Stack<E> flightStack; // for saving all items to be used for file saving

    /**
     *
     * @param item
     * @return
     */
    public boolean contains(E item){
        if (root != null) {
            if(root.getItem().equals(item)){
                return true;
            }
        }
        return containHelper(root, item);
    }

    /**
     *
     * @param node
     * @param item
     * @return
     */
    private boolean containHelper(SortBTreeNode node, E item){
        if (node == null){
            return false;
        }
        if (node.getItem().equals(item)){
            return true;
        }
        if (item.compareTo((E) node.getItem()) > 0) {
            return containHelper(node.getRight(), item);
        }
        if (item.compareTo((E) node.getItem()) < 0) {
            return containHelper(node.getLeft(), item);
        }
        return false; // not unique
    }

    /**
     *
     * @return
     */
    public int size(){
        return sizeHelper(root);
    }

    /**
     *
     * @param node
     * @return
     */
    private int sizeHelper(SortBTreeNode node){
        if (node == null){
            return 0;
        }
        return (1 + sizeHelper(node.getLeft()) + sizeHelper(node.getRight()));
    }

    /**
     *
     * @param item
     */
    void add(E item){
        if (root == null){
            root = new SortBTreeNode(item);
        }
        addHelper(root, item);
    }

    /**
     *
     * @param node
     * @param item
     */
    private void addHelper(SortBTreeNode node, E item){
        if (node == null){
            return;
        }
        if (item.compareTo((E) node.getItem()) > 0){
            if (node.getRight() == null) {
                node.setRight(new SortBTreeNode(item));
                return;
            } else {
                addHelper(node.getRight(), item);
            }
        }
        if (item.compareTo((E) node.getItem()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new SortBTreeNode(item));
                return;
            } else {
                addHelper(node.getLeft(), item);
            }
        }

    }

    /**
     *
     * @param item
     * @return
     */
    public boolean remove(E item){
        return removeHelper(root, item);
    }

    /**
     *
     * @param node
     * @param item
     * @return
     */
    private boolean removeHelper(SortBTreeNode node, E item){ // if choosing left most, start right, then always go left and find smallest (if going right, start left, then always go right and find largest)
        if (node == null){
            return false;
        }

        if (item.compareTo((E) node.getItem()) > 0) {
            if (node.getRight() != null) {
                if (node.getRight().getItem().equals(item)) {
                    if (node.getRight().getRight() == null) {
                        node.setRight(node.getRight().getLeft());
                    } else {
                        SortBTreeNode tempNode = node.getRight();
                        node.setRight(removeReplace(node.getRight().getRight()));
                        node.getRight().setRight(tempNode.getRight());
                        node.getRight().setLeft(tempNode.getLeft());
                    }
                    return true;
                }
            }
            return removeHelper(node.getRight(), item);
        }
        if (item.compareTo((E) node.getItem()) < 0) {
            if (node.getLeft() != null) {
                if (node.getLeft().getItem().equals(item)) {
                    if (node.getLeft().getRight() == null) {
                        node.setLeft(node.getLeft().getLeft());
                    } else {
                        SortBTreeNode tempNode = node.getLeft();
                        node.setLeft(removeReplace(node.getLeft().getRight()));
                        node.getLeft().setLeft(tempNode.getLeft());
                        node.getLeft().setRight(tempNode.getRight());
                    }
                    return true;
                }
            }
            return removeHelper(node.getLeft(), item);
        }
        return false;
    }

    /**
     *
     * @param node
     * @return
     */
    private SortBTreeNode removeReplace(SortBTreeNode node){
        if(node == null) {
            return null;
        } else if (node.getLeft() == null){
            return node;
        }
        if ((node.getLeft().getLeft() == null) && (node.getLeft().getRight() != null)) {
            SortBTreeNode tempNode = node.getLeft();
            node.setLeft(node.getLeft().getRight());
            return tempNode;
        } else if (node.getLeft().getLeft() == null){
            return node.getLeft();
        }
        return removeReplace(node.getLeft());
    }

    /**
     *
     * @return
     */
    public boolean isEmpty(){
        if (root == null) {
            return true;
        }
        return false;
    }

    /**
     *
     */
    public void display(){
        if (root != null) {
            displayHelper(root);
        } else {
            System.out.println("Empty!");
        }
    }

    /**
     *
     * @param node
     */
    private void displayHelper(SortBTreeNode node){
        if(node == null){
            return;
        }
        System.out.println("Parent: " + node.getItem());
        if(node.getRight() != null) {
            System.out.println("R.Child: " + node.getRight().getItem() + " ");
        }
        if(node.getLeft() != null) {
            System.out.println("L.Child: " + node.getLeft().getItem() + " ");
        }
        displayHelper(node.getRight());
        displayHelper(node.getLeft());

    }

    /**
     *
     * @return
     */
    public Stack saveTree(){
       traverse(root);
       return this.flightStack;
    }

    /**
     *
     * @param node
     */
    private void traverse(SortBTreeNode node){
        if (node == null){
            return;
        } else {
            flightStack.push((E)node.getItem());
            traverse(node.getLeft());
            traverse(node.getRight());
        }
    }
}
