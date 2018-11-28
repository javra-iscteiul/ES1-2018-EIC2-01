package BDA.Twitter;

import java.util.Date;

public class Mensagem {


		/**
		 * data do tweet
		 */

		private String date;
		/**
		 * user que fez do tweet
		 */

		private String user;
		/**
		 * conteudo do tweet
		 */
		private String content;

		
		
		/**
		 * @param from_to String
		 * @param subject String
		 * @param date String
		 * @param content String
		 */
		public Mensagem( String user,String date,  String content) {
		//	this.from_to = from_to;
			this.user=user;
			this.date = date;
			this.content = content;
		}



		/**
		 * Devolve a data da mensagem
		 * @return String
		 */
		public String getDate() {
			return date;
		}



		/**
		 * Altera a data da mensagem
		 * @param date String
		 */
		public void setDate(String date) {
			this.date = date;
		}


		/**
		 *  Devolve o assunto da mensagem
		 * @return String
		 */
		public String getUser() {
			return user;
		}



		/**
		 * Altera o assunto da mensagem
		 * @param subject String
		 */
		public void setUser(String user) {
			this.user = user;
		}




		/**
		 * Devolve o conteudo da mensagem
		 * @return String
		 */
		public String getContent() {
			return content;
		}


		/**
		 * Altera  o conteudo da mensagem
		 * @param content String
		 */
		public void setContent(String content) {
			this.content = content;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString(){
			return 	this.user + "\r\n" + 
						" '" + this.content + "' "  + "\r\n"  +
						"- " + this.date ;
			
		}

	
}
