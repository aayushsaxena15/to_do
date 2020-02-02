package models.todo;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.WhenCreated;
import com.fasterxml.jackson.annotation.JsonFormat;
import play.data.validation.Constraints;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;


@Entity
public class Task extends Model {

    @Id
    @GeneratedValue
    private int id;

    @Constraints.Required
    @Column(name = "text_col")
    private String text;

    @CreatedTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "creation_date")
    private Date created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "complete_date", insertable = false)
    private Date completeDate;

    @Column(insertable = false)
    private boolean completed = false;

    public Task(String text, Date created, Date completeDate, Boolean completed) {
        this.text = text;
        this.created = created;
        this.completeDate = completeDate;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
