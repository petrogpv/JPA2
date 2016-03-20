package ua.kiev.prog;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="Groups")
@NamedQuery(name="Group.findAll", query = "SELECT g FROM Group g")
public class Group {
    @Id
    @GeneratedValue
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Client> clients = new ArrayList<>();

    @JoinColumn(name = "course")
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    public Group() {}

    public Group(String name) {
        this.name = name;
    }

    public void addClient(Client client) {
        client.setGroup(this);
        clients.add(client);
    }

    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }
    public int getClientsSize(){
        return clients.size();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    public Course getCourse() {
        return this.course;
    }



    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", course_name='" + this.getCourse().getName() + '\'' +
                '}';
    }
}
