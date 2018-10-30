package lab02;

public class MyDictionary {

    private Node[] objArray;
    private int size;

    /* find next prime number */
    private static int nextPrime(int n) {
        int prime = 0, i, nextPrime;
        /* check first if this is a prime number */
        for(i=2; i<n/2; i++) {
            if(n%i == 0) {
                prime = 1;
                break;
            }
        }
        if(prime == 1) {
            /* no, try to find next one */
            nextPrime = n;
            prime = 1;
            while(prime != 0) {
                nextPrime++;
                prime = 0;
                for(i=2; i<nextPrime/2; i++) {
                    if(nextPrime%i == 0) {
                        prime = 1;
                        break;
                    }
                }
            }
            return (nextPrime);
        } else
            /* yes, return this as is */
            return (n);
    }

    static int hashCode(String s) {
        int length = s.length();
        int answer = 0;
        if (length % 2 == 1) { // length is odd
            answer = (int) s.charAt(length - 1);
            length--;
        }
        // length is now even
        for (int i = 0; i < length; i += 2) {
            // do two characters at a time
            answer += s.charAt(i);
            answer += ((int) s.charAt(i + 1)) << 16;
        }
        return (answer < 0) ? -answer : answer;
    }

    private int getIndex(String key) {
        return Math.abs(hashCode(key)) % size;
    }

    private class Node {

        private Object item;
        private String key;
        private Node next;

        public Node(Object item, String key) {
            this.item = item;
            this.key = key;
            this.next = null;
        }

        public Object getContent() {
            return item;
        }

        public Node getNext() {
            return next;
        }

        public String getKey() {
            return key;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    public MyDictionary(int n) {
        size = nextPrime(n);
        objArray = new Node[size];
    }

    Object get(String key) {
        int i = getIndex(key);
        if (objArray[i] == null) {
            return null;
        }
        Node n = objArray[i];

        while (n != null) {
            if (n.getKey().equals(key)) {
                return n.getContent();
            }
            n = n.getNext();
        }
        return null;
    }

    int put(Object item, String key) {
        int i = getIndex(key);
        if (objArray[i] == null) {
            objArray[i] = new Node(item, key);
        } else {
            Node n = objArray[i];
            while (n.getNext() != null) n = n.getNext();
            n.setNext(new Node(item, key));
        }
        return i;
    }

    int del(String key) {
        int i = getIndex(key);
        Node n = objArray[i];
        if (n == null) return -1;
        Node prev = null;
        while (n != null) {
            if (n.getKey().equals(key)) {
                if (prev == null) {
                    objArray[i] = n.getNext();
                } else {
                    prev.setNext(n.getNext());
                }
                return i;
            }
            prev = n;
            n = n.getNext();
        }

        return -1;
    }

    public void printDictionary() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objArray.length; i++) {
            sb.append(i + ": ");
            if (objArray[i] == null) {
                sb.append("[empty]");
            } else {
                Node n = objArray[i];
                sb.append(n.getContent().toString());
                while (n.getNext() != null) {
                    n = n.getNext();
                    sb.append(", ");
                    sb.append(n.getContent().toString());
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
