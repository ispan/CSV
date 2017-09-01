public class DTOPontunVasar {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pontunarN == null) ? 0 : pontunarN.hashCode());
		result = prime * result + ((vasaN == null) ? 0 : vasaN.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOPontunVasar other = (DTOPontunVasar) obj;
		if (pontunarN == null) {
			if (other.pontunarN != null)
				return false;
		} else if (!pontunarN.equals(other.pontunarN))
			return false;
		if (vasaN == null) {
			if (other.vasaN != null)
				return false;
		} else if (!vasaN.equals(other.vasaN))
			return false;
		return true;
	}

	private String pontunarN;
	private String vasaN;

	public DTOPontunVasar(String pontunarN, String vasaN) {
		this.pontunarN = pontunarN;
		this.vasaN = vasaN;
	}

	/*
	 * public String toString() { return "DTOPontunVasar [pontunarN=" +
	 * pontunarN + ",vasaN=" + vasaN + "]"; }
	 */

	public String getPontunarN() {
		return pontunarN;
	}

	public void setPontunarN(String pontunarN) {
		this.pontunarN = pontunarN;
	}

	public String getVasaN() {
		return vasaN;
	}

	public void setVasaN(String vasaN) {
		this.vasaN = vasaN;
	}

}
