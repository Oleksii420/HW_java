public class B_08_01<T> {

    private static class Node<T> {
        T value;
        Node<T> next;

        Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> top;

    public boolean isEmpty() {
        return top == null;
    }

    public void push(T element) {
        top = new Node<>(element, top);
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException();
        }
        T value = top.value;
        top = top.next;
        return value;
    }

    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException();
        }
        return top.value;
    }

    public int size() {
        return sizeRecursive(top);
    }

    private int sizeRecursive(Node<T> node) {
        if (node == null) return 0;
        return 1 + sizeRecursive(node.next);
    }

    public void print() {
        printRecursive(top);
        System.out.println();
    }

    private void printRecursive(Node<T> node) {
        if (node == null) return;
        System.out.print(node.value + " ");
        printRecursive(node.next);
    }
}

class Main {
    public static void main(String[] args) {
        B_08_01<String> stack = new B_08_01<>();

        stack.push("A");
        stack.push("B");
        stack.push("C");

        stack.print();

        System.out.println("Top: " + stack.peek()); // C
        System.out.println("Popped: " + stack.pop()); // C

        System.out.println("Size: " + stack.size()); // 2
    }
}

