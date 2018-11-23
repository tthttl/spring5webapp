package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.model.Author;
import guru.springframework.spring5webapp.model.Book;
import guru.springframework.spring5webapp.model.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DBInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public DBInitializer(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
           initDB();
    }

    private void initDB(){
        Author a1 = new Author("Eric", "Evans");
        Publisher p1 = new Publisher("pub", "address1");
        publisherRepository.save(p1);
        Book b1 = new Book("Domain Driven Design", "1234", p1);
        a1.getBooks().add(b1);
        b1.getAuthors().add(a1);
        authorRepository.save(a1);
        //mivel a book az author owning entityje, ezert amikor ezt elmentjuk az authornak mar kell hogy legyen idja, hogy
        //azt az id-t el tudjuk menteni, mint FK a jointable-be. => mindig az OWNED entityt kell elobb elmenteni, hogy az
        //owningnak legyen FK-ja
        bookRepository.save(b1);

        Author a2 = new Author("Rod", "Johnson");
        Publisher p2 = new Publisher("pub", "address2");
        publisherRepository.save(p2);
        Book b2 = new Book("J2EE Development without EJB", "23444", p2);
        a2.getBooks().add(b2);
        b2.getAuthors().add(a2);
        authorRepository.save(a2);
        bookRepository.save(b2);


    }

}
