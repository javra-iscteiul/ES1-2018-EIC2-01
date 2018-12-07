package BDA.Twitter;

import BDA.Mensagem;
import twitter4j.MediaEntity;

/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 Classe mensagem do twitter
 */
public class MensagemTwitter extends Mensagem{
	
		/**
		 * Vetor de imagens da mensagem
		 */
		private MediaEntity[] media;
	
		/**
		 * @param user String
		 * @param date String
		 * @param content String
		 */
		public MensagemTwitter( String user,String date,  String content) {
			super(user,date,content);
		}
 

		/**
		 * Devolve o vetor de imagens da mensagem
		 * @return MediaEntity[]
		 */
		public MediaEntity[] getMedia() {
			return media;
		}

		/**
		 * Altera o vetor de imagens da mensagem
		 * @param media MediaEntity[]
		 */

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

		/**
		 * Devolve se a mensagem contem o filtro indicado como parametro
		 * @param filter String
		 * @return boolean
		 */
		@Override
		public boolean containsFilter(String filter) {
			// TODO Auto-generated method stub
			return this.getUser().contains(filter) || this.getContent().contains(filter);
		}



		/**
		 * Devolve se a mensagem pertence ao user indicado como parametro
		 * @param filter String
		 * @return boolean
		 */
		@Override
		public boolean userContainsFilter(String filter) {
			// TODO Auto-generated method stub
			return this.getUser().contains(filter);
		}

	
}
