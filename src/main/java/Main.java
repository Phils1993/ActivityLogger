import app.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;

import static app.threads.Run.RunThreads;


public class Main {
    public static void main(String[] args) {

        final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        RunThreads();


        emf.close();
    }


}



