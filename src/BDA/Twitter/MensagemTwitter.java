package BDA.Twitter;

import java.util.Date;

import BDA.Mensagem;
import twitter4j.MediaEntity;

public class MensagemTwitter extends Mensagem{

		/**
		 * @param from_to String
		 * @param subject String
		 * @param date String
		 * @param content String
		 */
	
	
		private MediaEntity[] media;
	
		public MensagemTwitter( String user,String date,  String content) {
			super(user,date,content);
		}
 


		public MediaEntity[] getMedia() {
			return media;
		}



		public void setMedia(MediaEntity[] media) {
			this.media = media;
		}

		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString(){
			return 	this.getUser() + "\r\n" + 
						" '" + this.getContent() + "' "  + "\r\n"  +
						"- " + this.getDate() ;
			
		}

		@Override
		public boolean containsFilter(String filter) {
			// TODO Auto-generated method stub
			return this.getUser().contains(filter) || this.getContent().contains(filter);
		}




		@Override
		public boolean userContainsFilter(String filter) {
			// TODO Auto-generated method stub
			return this.getUser().contains(filter);
		}

	
}
