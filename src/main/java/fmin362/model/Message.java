package fmin362.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class Message
        implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    private Long id;

    @Column( length = 140 )
    private String text = "";

    @Column( name = "message_date" )
    @Temporal( javax.persistence.TemporalType.DATE )
    private Date date = new Date();

    public Message()
    {
        // Constructeur par défaut nécessaire pour JPA
    }

    public Message( String text, Date date )
    {
        this.text = text;
        this.date = date;
    }

    public Long getId()
    {
        return id;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;
    }

}
