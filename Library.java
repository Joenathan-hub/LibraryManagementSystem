import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private List<Member> members;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerMember(Member member) {
        members.add(member);
    }

    public List<Book> searchBookByTitle(String title) {
        List<Book> results = new ArrayList<>();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(b);
            }
        }
        return results;
    }

    // Enforces the "at most one active loan per book" business rule
    public boolean lendBook(String memberId, String isbn) {
        Member member = findMember(memberId);
        Book book = findBook(isbn);

        if (member == null || book == null) {
            System.out.println(">>> system Alert: Member or Book missing from records.");
            return false;
        }

        // Gracefully reject if the book is already out on loan
        if (!book.isAvailable()) {
            System.out.println(">>> REJECTED: \"" + book.getTitle() + "\" is already out on loan.");
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate due = today.plusDays(14); // 2-week loan period
        Loan newLoan = new Loan(member, book, today, due);

        book.setAvailable(false);
        member.getLoans().add(newLoan);
        System.out.println(">>> SUCCESS: Issued \"" + book.getTitle() + "\" to " + member.getName());
        return true;
    }

    public boolean returnBook(String isbn) {
        Book book = findBook(isbn);

        if (book == null) {
            System.out.println(">>> Error: Book not found.");
            return false;
        }

        if (book.isAvailable()) {
            System.out.println(">>> Error: Book is already on the shelf.");
            return false;
        }

        // Find the active loan and remove it from the member's log
        for (Member m : members) {
            for (int i = 0; i < m.getLoans().size(); i++) {
                Loan l = m.getLoans().get(i);
                if (l.getBook().getIsbn().equals(isbn)) {
                    m.getLoans().remove(i);
                    book.setAvailable(true);
                    System.out.println(">>> SUCCESS: \"" + book.getTitle() + "\" returned by " + m.getName());
                    return true;
                }
            }
        }
        return false;
    }

    private Member findMember(String id) {
        for (Member m : members) {
            if (m.getMemberId().equals(id)) return m;
        }
        return null;
    }

    private Book findBook(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) return b;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Library Status [Total Books=" + books.size() + ", Registered Members=" + members.size() + "]";
    }
}