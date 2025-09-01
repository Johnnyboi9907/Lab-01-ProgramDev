/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ziptestdemo;

/**
 *
 * @author John
 */
import java.util.HashMap;

public class ZipCode {
 public int Zip; // stored integer ZIP
 private boolean valid; // flag

 // Encoding table: maps digit -> 5-bit barcode
 private static final String[] DIGIT_CODES = {
 "11000", // 0
 "00011", // 1
 "00101", // 2
 "00110", // 3
 "01001", // 4
 "01010", // 5
 "01100", // 6
 "10001", // 7
 "10010", // 8
 "10100" // 9
 };

 private static final HashMap<String, Integer> codeToDigit = new HashMap<>();
 static {
 for (int i = 0; i < DIGIT_CODES.length; i++) {
 codeToDigit.put(DIGIT_CODES[i], i);
 }
 }

 // constructor for int Zip
 public ZipCode(int zip) {
 if (zip > 99999) {
 System.out.println(zip + " zip code is more than 5 digits.");
 this.Zip = zip; // still store it
 this.valid = false;
 } else if (zip < 0) {
 System.out.println(zip + " zip code is negative.");
 this.Zip = 0;
 this.valid = false;
 } else {
 this.Zip = zip;
 this.valid = true;
 }
 }

 // constructor for string barcode
 public ZipCode(String bar) {
 this.Zip = 0;
 this.valid = false;

 if (bar == null) {
 System.out.println("Error: null bar code.");
 return;
 }

 // testing for bad length (length must be 1 + (5*5) + 1 = 27 characters long)
 if (bar.length() != 27) {
 System.out.println("Error: bar code must be in multiples of 5-binary digits.");
 return;
 }

 // testing for bad start/end character
 if (bar.charAt(0) != '1' || bar.charAt(bar.length() - 1) != '1') {
 System.out.println("Error: bar code missing a 1 at start or end.");
 return;
 }

 // testing for bad digit
 for (int i = 0; i < bar.length(); i++) {
 char c = bar.charAt(i);
 if (c != '0' && c != '1') {
 System.out.println("bar code character: " + c + " must be '0' or '1'");
 return;
 }
 }

 // remove start/end 1's
 String middle = bar.substring(1, bar.length() - 1);

 // middle must be multiple of 5
 if (middle.length() != 25) {
 System.out.println("Error: bar code must be in multiples of 5-binary digits");
 return;
 }

 // decode
 int decoded = parseBarCode(middle);
 if (decoded >= 0) {
 this.Zip = decoded;
 this.valid = true;
 }
 }

 // public method: get barcode
 public String GetBarCode() {
 if (!valid) {
 return "ERROR";
 }

 String zipStr = String.format("%05d", this.Zip); // always 5 digits
 StringBuilder sb = new StringBuilder();
 sb.append("1"); // starting 1

 for (char c : zipStr.toCharArray()) {
 int digit = c - '0';
 sb.append(DIGIT_CODES[digit]);
 }

 sb.append("1"); // ending 1
 return sb.toString();
 }

 // private method: parse barcode
 private int parseBarCode(String middle) {
 StringBuilder zipStr = new StringBuilder();

 for (int i = 0; i < middle.length(); i += 5) {
 String chunk = middle.substring(i, i + 5);
 Integer digit = codeToDigit.get(chunk);
 if (digit == null) {
 System.out.println(chunk + " has invalid sequence in the bar code");
 return -1;
 }
 zipStr.append(digit);
 }

 return Integer.parseInt(zipStr.toString());
 }
}
