package exam.year23_24_code08.ex2;


import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client {
    private boolean isRunning;
    private IProductManager productManager;
    private Scanner scanner;

    public Client() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 3564);
        productManager = (IProductManager) registry.lookup("ProductManager");
        scanner = new Scanner(System.in);
        isRunning = true;
    }

    public void run() throws IOException, SQLException, ClassNotFoundException {
        System.out.println(productManager.sendWelcomeMessage());
        while (isRunning) {
            handleCommand(scanner.nextLine());
        }
    }

    private void handleCommand(String line) throws RemoteException {
        StringTokenizer str = new StringTokenizer(line, "\t");
        String command = str.nextToken().toUpperCase();
        String param = line.substring(command.length()).trim();

        switch (command) {
            case "ADD" -> add(param);
            case "BUY" -> buy(param);
            case "UPDATE" -> update(param);
            case "FIND" -> find(param);
            case "ALL" -> all();
            case "GET" -> get(param);
            case "DELETE" -> delete(param);
            case "QUIT" -> quit();
            default -> System.out.println("Invalid command");
        }
    }

    private void delete(String param) throws RemoteException {
        if (Utils.validateParams(param, 1)) {
            try {
                int id = Integer.parseInt(param);
                if (productManager.delete(id)) {
                    System.out.println("Product ID " + id + " deleted successfully.");
                } else {
                    System.out.println("Failed to delete product ID " + id);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid parameter format: " + e.getMessage());
            }
        }
    }

    private void get(String param) throws RemoteException {
        if (Utils.validateParams(param, 1)) {
            try {
                int id = Integer.parseInt(param);
                Product product = productManager.get(id);
                if (product != null) {
                    System.out.println(product);
                } else {
                    System.out.println("Product ID " + id + " not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid parameter format: " + e.getMessage());

            }
        }
    }

    private void all() throws RemoteException {
        List<Product> products = productManager.all();
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            products.forEach(System.out::println);
        }
    }

    private void quit() {
        isRunning = false;
    }

    private void find(String param) throws RemoteException {
        if (Utils.validateParams(param, 1)) {
            List<Product> products = productManager.find(param);
            if (products.isEmpty()) {
                System.out.println("No products found.");
            } else {
                products.forEach(System.out::println);
            }
        }
    }

    private void update(String param) throws RemoteException {
        if (Utils.validateParams(param, 2)) {
            List<String> params = Utils.parseParam(param);
            int id = Integer.parseInt(params.get(0));
            int count = Integer.parseInt(params.get(1));

            if (productManager.get(id) != null && productManager.update(id, new Product(count))) {
                System.out.println("Product ID " + id + " updated successfully.");
            } else {
                System.out.println("Failed to update product ID " + id);
            }
        }
    }

    private void buy(String param) throws RemoteException {
        List<Integer> ids = Utils.parseParamInt(param);
        if (ids.isEmpty()) {
            System.out.println("No valid product IDs to buy.");
            return;
        }

        Map<Integer, String> results = productManager.buy(ids);
        results.forEach((id, result) -> System.out.println(getBuyResultMessage(id, result)));
    }

    private String getBuyResultMessage(int id, String result) {
        return switch (result) {
            case "SUCCESS" -> "Product ID " + id + ": Successfully purchased.";
            case "OUT_OF_STOCK" -> "Product ID " + id + ": Out of stock.";
            case "NOT_FOUND" -> "Product ID " + id + ": Not found.";
            default -> "Product ID " + id + ": Unknown error.";
        };
    }

    private void add(String param) throws RemoteException {
        if (Utils.validateParams(param, 4)) {
            List<String> params = Utils.parseParam(param);
            try {
                int id = Integer.parseInt(params.get(0));
                String name = params.get(1);
                double price = Double.parseDouble(params.get(2));
                int count = Integer.parseInt(params.get(3));

                if (productManager.get(id) != null) {
                    System.out.println("Product with ID " + id + " already exists.");
                } else if (productManager.add(new Product(id, name, price, count))) {
                    System.out.println("Product added successfully.");
                } else {
                    System.out.println("Product not added.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid parameter format: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws IOException, NotBoundException, SQLException, ClassNotFoundException {
        new Client().run();
    }
}
