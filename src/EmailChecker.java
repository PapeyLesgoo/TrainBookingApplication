import java.io.*;
import java.net.*;
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.*;
import javax.naming.directory.*;
public class EmailChecker {
    private int hear( BufferedReader in ) throws IOException {
        String line = null;
        int res = 0;
        while ( (line = in.readLine()) != null ) {
            String pfx = line.substring( 0, 3 );
            try {
                res = Integer.parseInt( pfx );
            }
            catch (Exception ex) {
                res = -1;
            }
            if ( line.charAt( 3 ) != '-' ) break;
        }
        return res;
    }
    private void say( BufferedWriter wr, String text )
            throws IOException {
        wr.write( text + "\r\n" );
        wr.flush();
        return;
    }
    private ArrayList getMX( String hostName )
            throws NamingException {
        // Perform a DNS lookup for MX records in the domain
        Hashtable env = new Hashtable();
        env.put("java.naming.factory.initial",
                "com.sun.jndi.dns.DnsContextFactory");
        DirContext ictx = new InitialDirContext( env );
        Attributes attrs = ictx.getAttributes
                ( hostName, new String[] { "MX" });
        Attribute attr = attrs.get( "MX" );
        // if we don't have an MX record, try the machine itself
        if (( attr == null ) || ( attr.size() == 0 )) {
            attrs = ictx.getAttributes( hostName, new String[] { "A" });
            attr = attrs.get( "A" );
            if( attr == null )
                throw new NamingException
                        ( "No match for name '" + hostName + "'" );
        }
        // Huzzah! we have machines to try. Return them as an array list
        // NOTE: We SHOULD take the preference into account to be absolutely
        //   correct. This is left as an exercise for anyone who cares.
        ArrayList res = new ArrayList();
        NamingEnumeration en = attr.getAll();
        while ( en.hasMore() ) {
            String x = (String) en.next();
            String f[] = x.split( " " );
            if ( f[1].endsWith( "." ) )
                f[1] = f[1].substring( 0, (f[1].length() - 1));
            res.add( f[1] );
        }
        return res;
    }
    public boolean isAddressValid( String address ) {
        // Find the separator for the domain name
        int pos = address.indexOf( '@' );
        // If the address does not contain an '@', it's not valid
        if ( pos == -1 ) return false;
        // Isolate the domain/machine name and get a list of mail exchangers
        String domain = address.substring( ++pos );
        ArrayList mxList = null;
        try {
            mxList = getMX( domain );
        }
        catch (NamingException ex) {
            return false;
        }
        // Just because we can send mail to the domain, doesn't mean that the
        // address is valid, but if we can't, it's a sure sign that it isn't
        if ( mxList.size() == 0 ) return false;
        // Now, do the SMTP validation, try each mail exchanger until we get
        // a positive acceptance. It *MAY* be possible for one MX to allow
        // a message [store and forwarder for example] and another [like
        // the actual mail server] to reject it. This is why we REALLY ought
        // to take the preference into account.
        for ( int mx = 0 ; mx < mxList.size() ; mx++ ) {
            boolean valid = false;
            try {
                int res;
                Socket skt = new Socket( (String) mxList.get( mx ), 25 );
                BufferedReader rdr = new BufferedReader
                        ( new InputStreamReader( skt.getInputStream() ) );
                BufferedWriter wtr = new BufferedWriter
                        ( new OutputStreamWriter( skt.getOutputStream() ) );
                res = hear( rdr );
                if ( res != 220 ) throw new Exception( "Invalid header" );
                say( wtr, "EHLO orbaker.com" );
                res = hear( rdr );
                if ( res != 250 ) throw new Exception( "Not ESMTP" );
                // validate the sender address
                say( wtr, "MAIL FROM: <tim@orbaker.com>" );
                res = hear( rdr );
                if ( res != 250 ) throw new Exception( "Sender rejected" );
                say( wtr, "RCPT TO: <" + address + ">" );
                res = hear( rdr );
                // be polite
                say( wtr, "RSET" ); hear( rdr );
                say( wtr, "QUIT" ); hear( rdr );
                if ( res != 250 )
                    throw new Exception( "Address is not valid!" );
                valid = true;
                rdr.close();
                wtr.close();
                skt.close();
            }
            catch (Exception ex) {
                // Do nothing but try next host
            }
            finally {
                if ( valid ) return true;
            }
        }
        return false;
    }
    public  String call_this_to_validate( String email ) {
        String testData[] = {email};
        String return_string="";
        for ( int ctr = 0 ; ctr < testData.length ; ctr++ ) {
            return_string=( testData[ ctr ] + " is valid? " +
                    isAddressValid( testData[ ctr ] ) );
        }
        return return_string;
    }

    public void sent_Welcome_email(String recep){
        // email ID of Recipient.
        String recipient = recep;

        // email ID of  Sender.
        String sender = "k213894@nu.edu.pk";

        // using host as localhost
        String host = "127.0.0.1";

        // Getting system properties
        Properties properties = System.getProperties();

        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.port", "2525");
        //properties.put("mail.smtp.auth","true");

        properties.put("mail.smtp.starttls.enable", "true");

        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);

        try
        {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));

            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: subject of the email
            message.setSubject("Welcome Message");

            // set body of the email.
            message.setContent("<h1>Welcome to Train Booking Application</h1>","text/html");

            // Send email.
            Transport.send(message);
            System.out.println("Mail successfully sent");
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
        }
    }
}
