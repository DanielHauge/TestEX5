package Monopoly.Interfaces;

public interface IBank {


    public void Loan(IPlayer player, int amount);

    public void Pay(IPlayer player, int amount);


}
