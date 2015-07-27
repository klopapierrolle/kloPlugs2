package misc;

public enum JobTypes {
	PRIEST(5, "Priester", 0.1F), POLICEMAN(5, "Polizist", 0.1F), LAWYER(5, "Anwalt", 0.1F), MARKETMAN(0, "Händler", 0.0F), WEAPONIZER(0, "Waffenhändler", 0.0F), BORDELLMAN(0, "Puffbesitzer", 0.0F), JUDGE(5, "Richter", 0.1F), WITCH(5, "Hexe", 0.1F), INNHOUSE(5, "Gasthausleiter", 0.1F), BANKER(5, "Banker", 0.1F), HOE(0, "Prostituierte", 0.1F), CROUPIER(5, "Croupier", 0.1F), DOCTOR(5, "Arzt", 0.1F), HUNTER(5, "Jäger", 0.1F);
	
	private int money;
	private String name;
	private float tax;
	
	public int getMoney() {
		return money;
	}
	public String getName() {
		return name;
	}
	public float getTax() {
		return tax;
	}
	
	private JobTypes(final int money, final String name, final float tax) {
		this.money = money;
		this.name = name;
		this.tax = tax;
	}
}
