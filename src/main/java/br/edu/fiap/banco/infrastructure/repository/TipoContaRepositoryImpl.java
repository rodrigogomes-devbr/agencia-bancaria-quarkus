package br.edu.fiap.banco.infrastructure.repository;

import br.edu.fiap.banco.application.ports.TipoContaRepository;
import br.edu.fiap.banco.domain.model.TipoConta;
import br.edu.fiap.banco.application.exception.TipoContaNaoEncontradoException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TipoContaRepositoryImpl implements TipoContaRepository {
    private final DataSource dataSource;

    @Inject
    public TipoContaRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean existePorTipo(String nome) {
        String sqlQuery = "SELECT 1 FROM tipo_conta Where nome = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, nome);
            try (ResultSet resultado = statement.executeQuery()) {
                return resultado.next();
            }
        }catch(SQLException err){
            throw new IllegalStateException("Falha ao consultar tipo de conta.", err);
        }
    }

    @Override
    public TipoConta salvar(TipoConta tipoConta) {
        String sql = "INSERT INTO tipo_conta (nome) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tipoConta.nome());
            statement.executeUpdate();
            try (ResultSet resultado = statement.getGeneratedKeys()) {
                if (!resultado.next()) {
                    throw new IllegalStateException("O INSERT não devolveu o ID do tipo de conta.");
                }
                return tipoConta.comId(resultado.getLong("id"));
            }
        } catch (SQLException err) {
            throw new IllegalStateException("Falha ao salvar tipo de conta.", err);
        }
    }

    @Override
    public List<TipoConta> listarTodos() {
        String sql = "SELECT id, nome FROM tipo_conta ORDER BY nome";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {
            List<TipoConta> tipos = new ArrayList<>();
            while (resultado.next()) {
                tipos.add(new TipoConta(resultado.getLong("id"), resultado.getString("nome")));
            }
            return tipos;
        } catch (SQLException err) {
            throw new IllegalStateException("Falha ao listar tipos de conta.", err);
        }
    }

    @Override
    public Optional<TipoConta> buscarPorId(Long id) {
        String sql = "SELECT id, nome FROM tipo_conta WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultado = statement.executeQuery()) {
                if (!resultado.next()) {
                    return Optional.empty();
                }
                return Optional.of(new TipoConta(resultado.getLong("id"), resultado.getString("nome")));
            }
        } catch (SQLException err) {
            throw new IllegalStateException("Falha ao consultar tipo de conta.", err);
        }
    }

    @Override
    public TipoConta atualizar(TipoConta tipoConta) {
        String sql = "UPDATE tipo_conta SET nome = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tipoConta.nome());
            statement.setLong(2, tipoConta.id());
            if (statement.executeUpdate() == 0) {
                throw new TipoContaNaoEncontradoException(tipoConta.id());
            }
            return tipoConta;
        } catch (SQLException err) {
            throw new IllegalStateException("Falha ao atualizar tipo de conta.", err);
        }
    }

    @Override
    public boolean excluir(Long id) {
        String sql = "DELETE FROM tipo_conta WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException err) {
            throw new IllegalStateException("Falha ao excluir tipo de conta.", err);
        }
    }
}