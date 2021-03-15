package Servers.db;

import org.bson.Document;

import java.util.Date;
import java.util.Objects;

public class Client implements Documented {
    private final int id;
    private final Date start;
    private final Date finish;

    public Date getStart() {
        return start;
    }

    public int getId() {
        return id;
    }

    public Date getFinish() {
        return finish;
    }

    public Client(int id, Date start, Date finish) {
        this.id = id;
        this.start = start;
        this.finish = finish;
    }

    @Override
    public Document doc() {
        return new Document().append("id", id).append("start", start).append("finish", finish);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", start=" + start +
                ", finish=" + finish +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                Objects.equals(start, client.start) &&
                Objects.equals(finish, client.finish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, finish);
    }
}
