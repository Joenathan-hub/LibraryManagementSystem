public class LibraryDemo {
    public static void main(String[] args) {
        Library library = new Library();

        // Instantiate 3 books (using overloaded constructors)
        Book b1 = new Book("978-0134685991", "Effective Java", "Joshua Bloch");
        Book b2 = new Book("978-0132350884", "Clean Code", "Robert C. Martin");
        Book b3 = new Book("978-0596009205", "Head First Design Patterns"); // Uses 2-argument constructor

        library.addBook(b1);
        library.addBook(b2);
        library.addBook(b3);

        // Instantiate 2 members
        Member m1 = new Member("VU-001", "Kakooza John");
        Member m2 = new Member("VU-002", "Nsubuga Phiona");

        library.registerMember(m1);
        library.registerMember(m2);

        System.out.println("==================================================================");
        System.out.println("1. INITIAL LIBRARY STATE");
        System.out.println("==================================================================");
        System.out.println(library);
        System.out.println(b1);
        System.out.println(m1);
        System.out.println("==================================================================\n");

        System.out.println("--- Executing Borrowing Transactions ---");
        // Issue book cleanly to member 1
        library.lendBook("VU-001", "978-0134685991");

        // Attempting to borrow the same book (Triggers the graceful rule rejection)
        library.lendBook("VU-002", "978-0134685991");

        // Issue a different available book to member 2
        library.lendBook("VU-002", "978-0132350884");

        System.out.println("\n==================================================================");
        System.out.println("2. STATE AFTER LENDING ACTIONS");
        System.out.println("==================================================================");
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(m1);
        System.out.println(m2);
        System.out.println("==================================================================\n");

        System.out.println("--- Executing Return Transactions ---");
        library.returnBook("978-0134685991");

        System.out.println("\n==================================================================");
        System.out.println("3. FINAL STATE AFTER RETURNS");
        System.out.println("==================================================================");
        System.out.println(b1);
        System.out.println(m1);
        System.out.println("==================================================================");
    }
}