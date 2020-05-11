
package tk.sherrao.discord.dominatenetwork;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import tk.sherrao.logging.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterUpdater implements Runnable {

    private final Bot bot;
    private final JDA api;
    private final Logger log;
    
    private ConfigurationBuilder config;
    private TwitterFactory factory;
    private Twitter twitter;

    private Color aqua;
    private Status lastUpdate;
    private String twitterWebsiteUrl;
    private String twitterIconUrl;
    private String userImageUrl;
    private TextChannel channel;

    public TwitterUpdater( final Bot bot ) {
        this.bot = bot;
        this.api = bot.getApi();
        this.log = bot.getLog();
        try {
                
            this.config = new ConfigurationBuilder()
                    .setOAuthConsumerKey( BotInformation.TWITTER_CONSUMER_KEY )
                    .setOAuthConsumerSecret( BotInformation.TWITTER_CONSUMER_SECRET )
                    .setOAuthAccessToken( BotInformation.TWITTER_ACCESS_TOKEN )
                    .setOAuthAccessTokenSecret( BotInformation.TWITTER_ACCESS_SECRET )
                    .setDebugEnabled( true );
            
            this.factory = new TwitterFactory( config.build() );
            this.twitter = factory.getInstance();
            twitter.verifyCredentials();

            this.aqua = new Color( 0, 255, 255 );
            this.lastUpdate = twitter.showUser( BotInformation.TWITTER_PROFILE ).getStatus();
            this.twitterWebsiteUrl = "https://www.twitter.com/";
            this.twitterIconUrl = "https://www.iconfinder.com/icons/294709/download/png/512";
            this.userImageUrl = twitter.showUser( BotInformation.TWITTER_PROFILE ).getProfileImageURL();
            this.channel = api.getTextChannelById( BotInformation.TWITTER_CHANNEL );
            
        } catch ( TwitterException e ) {
            log.error( "Failed to connect to Twitter. The internet connection on this machine, or Twitter might be down at the moment", e );

        }

    }
    
    @Override
    public void run() {
        try {
            Status status = twitter.showUser( BotInformation.TWITTER_PROFILE ).getStatus();
            if( status.getCreatedAt().after( lastUpdate.getCreatedAt() ) ) {
                lastUpdate = status;
                channel.sendMessage( new EmbedBuilder()
                        .setAuthor( lastUpdate.getCreatedAt().toString(), twitterWebsiteUrl, twitterIconUrl )
                        .setTitle( lastUpdate.getText() )
                        .setThumbnail( userImageUrl )
                        .setColor( aqua )
                        .build() )
                
                    .complete();
                
            } else 
                return;
        
        } catch ( TwitterException e ) {
            log.error( "Failed to connect with Twitter!", e );
            
        }
        
    }
    
    public Bot getBot() {
        return bot;
        
    }
    
}
