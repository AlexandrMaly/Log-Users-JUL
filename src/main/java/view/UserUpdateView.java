package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserUpdateView {

    public Map<String, String> getData() {
        System.out.println("\nUPDATE FORM");
        Map<String, String> map = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input id: ");
        map.put("id", scanner.nextLine().trim());
        System.out.print("Input  name: ");
        map.put("name", scanner.nextLine().trim());
        System.out.print("Input email in format example@example.com: ");
        map.put("email", scanner.nextLine().trim());
        return map;
    }

    public void getOutput(String output) {
        System.out.println(output);
    }
}
