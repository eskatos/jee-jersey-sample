package fmin362.model;

import java.util.Date;

public class Message
{

    private String text;

    private Date date;

    public Message( String text, Date date )
    {
        this.text = text;
        this.date = date;
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
