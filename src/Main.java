import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Olá usuário seja bem-vindo! \n Digite 1 - para Adicionar um aluno \n Digite 2 - Listar alunos");
        int action = scan.nextInt();

        try(var connection = DB.connect()){
            switch(action) {
                case 1:
                    System.out.println("Digite seu nome");
                    String nome = scan.next();

                    System.out.println("Digite seu cpf");
                    String cpf = scan.next();

                    String query = "INSERT INTO aluno(cpf, nome) VALUES(?,?)";
                    PreparedStatement stmt = connection.prepareStatement((query));
                    stmt.setString(1, nome);
                    stmt.setString(2, cpf);
                    stmt.execute();

                    System.out.println("Aluno adicionado com sucesso!");
                    break;

                case 2:
                    List<Aluno> alunos = new ArrayList<>();
                    String conexao = "SELECT * FROM aluno";
                    PreparedStatement stmtConexao = connection.prepareStatement(conexao);
                    ResultSet rs = stmtConexao.executeQuery();
                    while (rs.next()) {
                        alunos.add(new Aluno(rs.getString("cpf"), rs.getString("nome")));
                    }
                    alunos.forEach(System.out::println);
                    break;
                default:
                    System.out.println("Digite um número válido!");
                    break;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());

        }
    }
}
