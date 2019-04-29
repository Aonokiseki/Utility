package execrise;

public class RolePlayGameAttributeResearch {
	public static void main(String[] args){
		
		Role defender = new Role("Player", 150, 1.1, 50, 0.95, 100, 1.25, 20, 1.05, 20, 1.1, 25, 1.05, 10, new Weapon(0, 1500, 0,0,0,0), new Armor(2000, 0, 1500, 0,0,0), new Jewelry[]{Jewelry.NULL, Jewelry.NULL});
		defender.setPhysicalDefend((int)(defender.physicalDefend()* 1.25));
		Role attacker = new Role("BOSS", 160, 5.5, 0, 1.55, 150, 1.45, 90, 1.45, 100, 1.35, 100, 1.2, 20, new Weapon(0, 1000, 0,0,0,0), Armor.NULL, new Jewelry[]{Jewelry.NULL, Jewelry.NULL});
		System.out.println("attacker == "+attacker);
		System.out.println("defender == "+defender);
		int damage = (int)Damage.execute(attacker, defender, new Skill(Skill.POWER_RANK.D));
		System.out.println("damage == "+damage);
		int nowHp = defender.hp() - damage;
		System.out.println("defender.hp() == "+nowHp);
	}
}

class Damage{
	public static double execute(Role attacker, Role defender, Skill skill){
		double finalDamage = 0.0;
		double skillPowerFix = attacker.physicalAttack() - defender.physicalDefend();
		double final_skillpower = skill.power() * (1 + skillPowerFix * 0.0005);
		System.out.println("final_skillpower == "+final_skillpower);
		double attackerATKFix = (attacker.physicalAttack() + attacker.weapon().physicalAttack() - defender.physicalDefend() - defender.armor().physicalDefend()) * 0.001;
		System.out.println("attackerATKFix == "+attackerATKFix);
		double final_attacker_attack = (attacker.physicalAttack() + attacker.weapon().physicalAttack()) * (1 + attackerATKFix + final_skillpower);
		double final_defender_defend = (defender.physicalDefend() + defender.armor().physicalDefend());
		finalDamage= (0.9 + Math.random()*0.2) * (final_attacker_attack - final_defender_defend);
		if(finalDamage < 0)
			finalDamage = 0.0;
		return finalDamage;
	}
}

class Skill{
	public static enum POWER_RANK{
		E(0.9),
		D(1.0),
		D_PLUS(1.1),
		C(1.25),
		C_PLUS(1.35),
		B(1.5),
		B_PLUS(1.6),
		A(1.75),
		A_PLUS(1.85),
		S(2.0),
		S_PLUS(2.1),
		SS(2.25),
		SSS(2.5),
		SSSS(2.75),
		SSSSS(3.0);
		
		private double power;
		private POWER_RANK(double power){
			this.power = power;
		}
		public double power(){
			return this.power;
		}
	};
	
	private POWER_RANK rank;
	public Skill(POWER_RANK rank){
		this.rank = rank;
	}
	public double power(){
		return this.rank.power;
	}
}

class Role{
	
	private static final double GLOBAL_BASIC_ABILITY_STATE_FIX = 0.05;
	private static final double GLOBAL_SPD_FIX = 0.0025;
	
	private String name;
	private int level;
	private int HP;
	private int physicalAttack;
	private int physicalDefend;
	private int magicAttack;
	private int magicDefend;
	private int speed;
	private Weapon weapon;
	private Armor armor;
	private Jewelry[] jewelrys;
	
	public String name(){
		return name;
	}
	public int level(){
		return level;
	}
	public int hp(){
		return HP;
	}
	public int physicalAttack(){
		return physicalAttack;
	}
	public int physicalDefend(){
		return physicalDefend;
	}
	public int magicAttack(){
		return magicAttack;
	}
	public int magicDefend(){
		return magicDefend;
	}
	public int speed(){
		return speed;
	}
	public Weapon weapon(){
		return this.weapon;
	}
	public Armor armor(){
		return this.armor;
	}
	public Jewelry[] jewelrys(){
		return jewelrys;
	}
	
	public void setPhysicalDefend(int physicalDefend){
		this.physicalDefend = physicalDefend;
	}
	public void setPhysicalAttack(int physicalAttack){
		this.physicalAttack = physicalAttack;
	}
	
	@Override
	public String toString(){
		return "[name="+name+", level="+level+", hp="+HP+", physicalAttack="+physicalAttack+", physicalDefend="+physicalDefend+
				", magicAttack="+magicAttack+", magicDefend="+magicDefend+", speed="+speed+", weapon.physicalAttack="+weapon.physicalAttack()+", armor.physicalDefend="+armor.physicalDefend()+"]";
	}
	
	public Role(
			String name, 
			int level, 
			double hp_multi_fix_param,
			int hp_add_fix_param,
			double physicalAttack_multi_fix_param,
			int physicalAttack_add_fix_param,
			double physicalDefend_multi_fix_param,
			int physicalDefend_add_fix_param,
			double magicAttack_multi_fix_param,
			int magicAttack_add_fix_param,
			double magicDefend_multi_fix_param,
			int magicDefend_add_fix_param,
			double speed_multi_fix_param,
			int speed_add_fix_param,
			Weapon weapon,
			Armor armor,
			Jewelry[] jewelrys
			){
		this.name = name;
		if(level > 200) level = 200;
		this.level = level;
		int basicStateStandard = (level + 15) * (level + 16);
		int basicAbilityStandard = (int)(GLOBAL_BASIC_ABILITY_STATE_FIX * basicStateStandard);
		int basicSpeedStandard = (int)(GLOBAL_SPD_FIX * basicStateStandard);
		
		this.HP = (int)(hp_multi_fix_param * basicStateStandard) + hp_add_fix_param;
		this.physicalAttack = (int)(physicalAttack_multi_fix_param * basicAbilityStandard + physicalAttack_add_fix_param);
		this.physicalDefend = (int)(physicalDefend_multi_fix_param * basicAbilityStandard + physicalDefend_add_fix_param);
		this.magicAttack = (int)(magicAttack_multi_fix_param * basicAbilityStandard + magicAttack_add_fix_param);
		this.magicDefend = (int)(magicDefend_multi_fix_param * basicAbilityStandard + magicDefend_add_fix_param);
		this.speed = (int)(speed_multi_fix_param * basicSpeedStandard + speed_add_fix_param);
		
		this.weapon = weapon;
		this.armor = armor;
		this.jewelrys = jewelrys;
	}
	
}

abstract class Equip{
	private int hitPoints;
	private int physicalAttack;
	private int physicalDefend;
	private int magicAttack;
	private int magicDefend;
	private int speed;
	
	public Equip(int hitPoints, int physicalAttack, int physicalDefend, int magicAttack, int magicDefend, int speed){
		this.hitPoints = hitPoints;
		this.physicalAttack = physicalAttack;
		this.physicalDefend = physicalDefend;
		this.magicAttack = magicAttack;
		this.magicDefend = magicDefend;
		this.speed = speed;
	}
	
	public int physicalAttack(){
		return this.physicalAttack;
	}
	public int physicalDefend(){
		return this.physicalDefend;
	}
	public int magicAttack(){
		return this.magicAttack;
	}
	public int magicDefend(){
		return this.magicDefend;
	}
	public int speed(){
		return this.speed;
	}
	public int hitPoints(){
		return this.hitPoints;
	}
}

class Weapon extends Equip{
	
	public Weapon(int hitPoints, int physicalAttack, int physicalDefend,
			int magicAttack, int magicDefend, int speed) {
		super(hitPoints, physicalAttack, physicalDefend, magicAttack, magicDefend,
				speed);
	}
	
	private static class NullWeapon extends Weapon{
		public NullWeapon(int hitPoints, int physicalAttack,
				int physicalDefend, int magicAttack, int magicDefend, int speed) {
			super(hitPoints, physicalAttack, physicalDefend, magicAttack, magicDefend,
					speed);
		}
	}
	public static final NullWeapon NULL = new NullWeapon(0,0,0,0,0,0);
}

class Armor extends Equip{

	public Armor(int hitPoints, int physicalAttack, int physicalDefend,
			int magicAttack, int magicDefend, int speed) {
		super(hitPoints, physicalAttack, physicalDefend, magicAttack, magicDefend,
				speed);
	}
	
	private static class NullArmor extends Armor{
		public NullArmor(int hitPoints, int physicalAttack, int physicalDefend,
				int magicAttack, int magicDefend, int speed) {
			super(hitPoints, physicalAttack, physicalDefend, magicAttack, magicDefend,
					speed);
		}
	}
	public static final NullArmor NULL = new NullArmor(0,0,0,0,0,0);
}

class Jewelry extends Equip{

	public Jewelry(int hitPoints, int physicalAttack, int physicalDefend,
			int magicAttack, int magicDefend, int speed) {
		super(hitPoints, physicalAttack, physicalDefend, magicAttack, magicDefend,
				speed);
	}
	
	private static class NullJewelry extends Jewelry{
		public NullJewelry(int hitPoints, int physicalAttack,
				int physicalDefend, int magicAttack, int magicDefend, int speed) {
			super(hitPoints, physicalAttack, physicalDefend, magicAttack, magicDefend,
					speed);
		}
	}
	public static final NullJewelry NULL = new NullJewelry(0,0,0,0,0,0);
}