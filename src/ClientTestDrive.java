public class ClientTestDrive {

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            SimpleClient client = new SimpleClient();
            client.go();
        }
    }
}