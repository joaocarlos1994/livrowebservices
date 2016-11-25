package br.com.livro.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarroDao extends BaseDAO {

	public Carro getCarroById(final Long id) throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;

		try {

			conn = getConnection();
			stmt = conn.prepareStatement("select * from carro where id=?");
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				final Carro c = createCarro(rs);
				rs.close();
				return c;
			}

		} finally {

			if (stmt != null) {
				stmt.close();
			}

			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	public List<Carro> findByName(final String name) throws SQLException {

		final List<Carro> carros = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;

		try {

			conn = getConnection();
			stmt = conn.prepareStatement("select * from carro where lower(nome) like ?");
			stmt.setString(1, "%" + name.toLowerCase() + "%");

			final ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				final Carro c = createCarro(rs);
				carros.add(c);
			}

			rs.close();

		} finally {

			if (stmt != null) {
				stmt.close();
			}

			if (conn != null) {
				conn.close();
			}

		}
		return Collections.unmodifiableList(carros);
	}

	public List<Carro> findByTipo(final String tipo) throws SQLException {
		final List<Carro> carros = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from carro where tipo = ?");
			stmt.setString(1, tipo);
			final ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				final Carro c = createCarro(rs);
				carros.add(c);
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return Collections.unmodifiableList(carros);
	}

	public List<Carro> getCarros() throws SQLException {
		final List<Carro> carros = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from carro");
			final ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				final Carro c = createCarro(rs);
				carros.add(c);
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return Collections.unmodifiableList(carros);
	}

	public Carro createCarro(final ResultSet rs) throws SQLException {
		final Carro carro = new Carro(rs.getLong("id"), rs.getString("tipo"), rs.getString("nome"), rs.getString("descricao"),
				rs.getString("url_foto"), rs.getString("url_video"), rs.getString("latitude"),
				rs.getString("longitude"));

		return carro;
	}

	public void save(final Carro c) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			if (c.getId() == null) {
				stmt = conn.prepareStatement(
						"insert into carro (nome,descricao,url_foto,url_video,latitude,longitude,tipo) VALUES(?,?,?,?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
			} else {
				stmt = conn.prepareStatement(
						"update carro set nome=?,descricao=?,url_foto=?,url_video=?,latitude=?,longitude=?,tipo=? where id=?");
			}
			stmt.setString(1, c.getNome());
			stmt.setString(2, c.getDesc());
			stmt.setString(3, c.getUrlFoto());
			stmt.setString(4, c.getUrlVideo());
			stmt.setString(5, c.getLatitude());
			stmt.setString(6, c.getLongitude());
			stmt.setString(7, c.getTipo());
			if (c.getId() != null) {
				// Update
				stmt.setLong(8, c.getId());
			}
			int count = stmt.executeUpdate();
			if (count == 0) {
				throw new SQLException("Erro ao inserir o carro");
			}
			// Se inseriu, ler o id auto incremento
			if (c.getId() == null) {
				// Long id = getGeneratedId(stmt);
				// c.setId(id);
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	// id gerado com o campo auto incremento
	public static Long getGeneratedId(final Statement stmt) throws SQLException {
		ResultSet rs = stmt.getGeneratedKeys();
		if (rs.next()) {
			Long id = rs.getLong(1);
			return id;
		}
		return 0L;
	}
	
	public boolean delete(final Long id) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("delete from carro where id=?");
			stmt.setLong(1, id);
			final int count = stmt.executeUpdate();
			final boolean ok = count > 0;
			return ok;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

}
