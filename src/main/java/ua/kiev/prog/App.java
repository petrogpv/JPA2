package ua.kiev.prog;

import javax.persistence.*;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPATest");
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("------------------ #1 ------------------");

            em.getTransaction().begin();
            try {
                Group group1 = new Group("Group-1");
                Group group2 = new Group("Group-2");
                Group group3 = new Group("Group-3");
                Group[] groups = new Group[]{group1, group2, group3};
                Course course = new Course("Course-Pro");

                for (Group g : groups) {
                    course.addGroup(g);
                }
                em.persist(course);

                for (int i = 0; i < 15; i++) {
                    Client client = new Client("Client-" + i, i);
                    Group group = groups[i % groups.length];
                    group.addClient(client);
                }

                for (Group g : groups)
                    em.persist(g);

                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
                return;
            }

            Query query = em.createNamedQuery("Group.findAll", Group.class);
            List<Group> grps = query.getResultList();

            for (Group gr : grps) {
                System.out.println("Group " + gr.getName() + " contains " + gr.getClientsSize() + " clients");
            }


            System.out.println("------------------ #2 ------------------");

            try {
                query = em.createNamedQuery("Course.findByName", Course.class);
                query.setParameter("name", "Course-Pro");
                Course crs = (Course) query.getSingleResult();

                System.out.println("Course " + crs.getName() + " contains:");
                for (Group g : crs.getGroupList()) {
                    System.out.println(g);
                    for (Client c : g.getClients())
                        System.out.println(c);
                }
            } catch (NoResultException ex) {
                System.out.println("Course not found!");
                return;
            } catch (NonUniqueResultException ex) {
                System.out.println("Non unique course found!");
                return;
            }
        }finally {
            em.close();
            emf.close();
        }
    }
}

