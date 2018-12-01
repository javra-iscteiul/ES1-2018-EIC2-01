package BDA.Twitter;

import java.util.Date;

import BDA.Mensagem;

public class MensagemTwitter extends Mensagem{

		/**
		 * @param from_to String
		 * @param subject String
		 * @param date String
		 * @param content String
		 */
		public MensagemTwitter( String user,String date,  String content) {
			super(user,date,content);
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
			return false;
		}




		@Override
		public boolean userContainsFilter(String filter) {
			// TODO Auto-generated method stub
			return false;
		}

	
}
