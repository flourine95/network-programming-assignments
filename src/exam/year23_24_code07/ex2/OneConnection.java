package exam.year23_24_code07.ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OneConnection implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final Dao dao;

    public OneConnection(Socket socket) throws IOException, SQLException, ClassNotFoundException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.dao = new Dao();
    }

    @Override
    public void run() {
        try {
            handleClient();
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private void handleClient() throws IOException {
        String clientRequest;
        while (true) {
            clientRequest = in.readLine();
            if (clientRequest == null || clientRequest.trim().isEmpty()) {
                continue;
            }

            String[] tokens = clientRequest.trim().split("\\s+", 2);
            String command = tokens[0].toUpperCase();
            String param = tokens.length > 1 ? tokens[1] : "";
            String response;
            switch (command) {
                case "ADD" -> response = handleAdd(param);
                case "BUY" -> response = handleBuy(param);
                case "UPDATE" -> response = handleUpdate(param);
                case "FIND" -> response = handleFind(param);
                case "QUIT" -> {
                    closeResources();
                    return;
                }
                default -> response = Response.error(MessageTemplate.INVALID_COMMAND);
            }
            out.println(response);
        }
    }


    private String handleAdd(String param) {
        if (!Utils.validateParams(param, 4)) {
            return Response.error(MessageTemplate.INVALID_PARAMETER);
        }
        List<String> params = Utils.parseParam(param);
        try {
            int id = Integer.parseInt(params.get(0));
            if (dao.get(id) != null) {
                return Response.error(MessageTemplate.PRODUCT_EXISTS);
            }
            String name = params.get(1);
            double price = Double.parseDouble(params.get(2));
            int count = Integer.parseInt(params.get(3));

            Product product = new Product(id, name, price, count);
            dao.add(product);
            return dao.add(product) ? Response.success(MessageTemplate.PRODUCT_ADDED) : Response.error(MessageTemplate.UNKNOWN_ERROR);
        } catch (NumberFormatException e) {
            return Response.error(MessageTemplate.NUMBER_FORMAT_ERROR);
        }
    }

    private String handleBuy(String param) {
        try {
            List<Integer> params = Utils.parseParamInt(param);
            Map<Integer, String> results = dao.buy(params);
            StringBuilder response = new StringBuilder();
            for (Map.Entry<Integer, String> entry : results.entrySet()) {
                response.append("Product ID: ").append(entry.getKey())
                        .append(", Status: ").append(entry.getValue()).append("\n");
            }

            return response.toString();
        } catch (NumberFormatException e) {
            System.err.println("Parameter conversion error: " + e.getMessage());
            return "ERROR: Invalid parameter format!";
        } catch (Exception e) {
            System.err.println("Unknown error: " + e.getMessage());
            return "ERROR: Unknown error occurred!";
        }

    }

    private String handleUpdate(String param) {
        if (!Utils.validateParams(param, 2)) {
            return Response.error(MessageTemplate.INVALID_PARAMETER);
        }
        List<String> params = Utils.parseParam(param);
        try {
            int id = Integer.parseInt(params.getFirst());
            Product product = dao.get(id);
            if (product == null) {
                return Response.error(MessageTemplate.PRODUCT_NOT_FOUND);
            }
            int newCount = Integer.parseInt(params.get(1));
            product.setCount(newCount);
            boolean updated = dao.update(id, product);
            return updated ? Response.success(MessageTemplate.PRODUCT_UPDATED) : Response.error(MessageTemplate.UNKNOWN_ERROR);
        } catch (NumberFormatException e) {
            return Response.error(MessageTemplate.NUMBER_FORMAT_ERROR);
        }
    }

    private String handleFind(String param) {
        if (!Utils.validateParams(param, 1)) {
            return Response.error(MessageTemplate.INVALID_PARAMETER);
        }
        List<Product> products = dao.find(param.trim());

        if (products.isEmpty()) {
            return Response.error(MessageTemplate.PRODUCT_NOT_FOUND);
        }

        StringBuilder response = new StringBuilder("Product list:\n");
        for (Product product : products) {
            response.append(String.format("ID: %d, Name: %s, Price: %.2f, Count: %d\n",
                    product.getId(), product.getName(), product.getPrice(), product.getCount()));
        }

        return response.toString();
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}
