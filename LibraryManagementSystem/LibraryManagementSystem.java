import java.util.Scanner;

public class LibraryManagementSystem {
    static String[] bookTitles = new String[100];
    static String[] authors = new String[100];
    static String[] isbns = new String[100];
    static int[] quantities = new int[100];
    static String[] patrons = new String[100];

    static int bookCount = 0;
    static int transactionCount = 0;

    static int findBookIndex(String isbn) {
        for (int i = 0; i < bookCount; i++) {
            if (isbns[i].equals(isbn)) {
                return i;
            }
        }
        return -1;
    }

    static int calculateBorrowedBooks() {
        int borrowedBooks = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (patrons[i] != null) {
                borrowedBooks++;
            }
        }
        return borrowedBooks;
    }

    static void addBook(String title, String author, String isbn, int quantity) {
        bookTitles[bookCount] = title;
        authors[bookCount] = author;
        isbns[bookCount] = isbn;
        quantities[bookCount] = quantity;
        bookCount++;
    }

    static void checkOutBook(String patronName, String isbn) {
        int bookIndex = findBookIndex(isbn);
        if (bookIndex != -1 && quantities[bookIndex] > 0) {
            patrons[transactionCount] = patronName + " aldı: " + bookTitles[bookIndex];
            quantities[bookIndex]--;
            transactionCount++;
            System.out.println("İşlem başarılı: Kitap alındı.");
        } else {
            System.out.println("Hata: Kitap mevcut değil veya stokta yok.");
        }
    }

    static void returnBook(String patronName, String isbn) {
        int bookIndex = findBookIndex(isbn);
        if (bookIndex != -1) {
            for (int i = 0; i < transactionCount; i++) {
                if (patrons[i] != null && patrons[i].startsWith(patronName) && patrons[i].contains(isbn)) {
                    patrons[i] = null;
                    quantities[bookIndex]++;
                    System.out.println("İşlem başarılı: Kitap iade edildi.");
                    return;
                }
            }
            System.out.println("Hata: Bu kitap bu patrona ait değil.");
        } else {
            System.out.println("Hata: Kitap mevcut değil.");
        }
    }

    static void viewAvailableBooks() {
        System.out.println("Mevcut Kitaplar:");
        for (int i = 0; i < bookCount; i++) {
            System.out.println("Başlık: " + bookTitles[i] + ", Yazar: " + authors[i] + ", ISBN: " + isbns[i] + ", Miktar: " + quantities[i]);
        }
    }

    static void searchBooks(String keyword) {
        System.out.println("Arama Sonuçları:");
        for (int i = 0; i < bookCount; i++) {
            if (bookTitles[i].toLowerCase().contains(keyword.toLowerCase()) || authors[i].toLowerCase().contains(keyword.toLowerCase()) || isbns[i].toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("Başlık: " + bookTitles[i] + ", Yazar: " + authors[i] + ", ISBN: " + isbns[i] + ", Miktar: " + quantities[i]);
            }
        }
    }

    static void generateReports() {
        System.out.println("Raporlar:");
        System.out.println("Toplam Kitap Sayısı: " + bookCount);
        System.out.println("Mevcut Kitaplar: " + (bookCount - calculateBorrowedBooks()));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Kitap Ekle\n2. Kitap Alma\n3. Kitap İade Et\n4. Mevcut Kitapları Görüntüle\n" + "5. Kitap Ara\n6. Rapor Oluştur\n0. Çıkış\n");
            System.out.print("Yapmak istediğiniz işlemi seçin: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // dummy nextLine to consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Kitap Başlığı: ");
                    String title = scanner.nextLine();
                    System.out.print("Yazar: ");
                    String author = scanner.nextLine();
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Miktar: ");
                    int quantity = scanner.nextInt();

                    addBook(title, author, isbn, quantity);
                    break;
                case 2:
                    System.out.print("Patron Adı: ");
                    String patronName = scanner.nextLine();
                    System.out.print("ISBN: ");
                    String borrowIsbn = scanner.nextLine();

                    checkOutBook(patronName, borrowIsbn);
                    break;
                case 3:
                    System.out.print("Patron Adı: ");
                    String returnPatronName = scanner.nextLine();
                    System.out.print("ISBN: ");
                    String returnIsbn = scanner.nextLine();

                    returnBook(returnPatronName, returnIsbn);
                    break;
                case 4:
                    viewAvailableBooks();
                    break;
                case 5:
                    System.out.print("Arama Kelimesi: ");
                    String keyword = scanner.nextLine();

                    searchBooks(keyword);
                    break;
                case 6:
                    generateReports();
                    break;
                case 0:
                    System.out.println("Çıkış yapılıyor...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Geçersiz bir seçenek girdiniz. Lütfen tekrar deneyin.");
            }
        }
    }
}