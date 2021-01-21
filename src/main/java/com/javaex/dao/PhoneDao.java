package com.javaex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository
public class PhoneDao {
	
	@Autowired
	private DataSource dataSource;	
	
	
	// 필드
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// 생성자
	// 메소드 g/s
	// 메소드 일반
	private void getDriver() {

		try {
			conn = dataSource.getConnection();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// getDriver()

	private void close() {
		try {
			if (rs != null)
				rs.close();

			if (conn != null)
				conn.close();

			if (pstmt != null)
				pstmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// close()
		
	// 사람정보 가져오기
	public PersonVo getPerson(int personId) {
		getDriver();
		PersonVo personVo = null;
		try {
			String query = "";
			query += " select person_id, ";
			query += "        name, ";
			query += "        hp, ";
			query += "        company ";
			query += " from person ";
			query += " where person_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				personVo= new PersonVo(id, name, hp, company);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return personVo;
	}//getPerson()

	public int personInsert(PersonVo pv) {
		int count = 0;
		getDriver();

		try {
			String query = "";
			query += " INSERT INTO person ";
			query += " VALUES(seq_person_id.nextval,?,?,?) ";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, pv.getName());
			pstmt.setString(2, pv.getHp());
			pstmt.setString(3, pv.getCompany());

			count = pstmt.executeUpdate();

			// 실행
			System.out.println("[" + count + "건 등록되었습니다.]");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return count;
	}// phoneInsert

	public int personUpdate(PersonVo pv) {
		getDriver();
		int count = 0;

		try {
			String query = "";
			query += " UPDATE person ";
			query += " SET name = ?, ";
			query += "     hp = ?, ";
			query += "     company = ? ";
			query += " WHERE person_id = ? ";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, pv.getName());
			pstmt.setString(2, pv.getHp());
			pstmt.setString(3, pv.getCompany());
			pstmt.setInt(4, pv.getPersonId());

			count = pstmt.executeUpdate();

			// 실행
			System.out.println("[" + count + "건이 수정되었습니다.]");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return count;

	}// phoneUpdate

	public int personDelete(int id) {
		getDriver();
		int count = 0;
		try {
			String query = "";
			query += " DELETE FROM person ";
			query += " WHERE person_id = ? ";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, id);
			count = pstmt.executeUpdate();

			// 실행
			System.out.println("[" + count + "건이 삭제되었습니다.]");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		close();
		return count;
	}// phoneDelete

	public List<PersonVo> getPersonList() {
		getDriver();
		List<PersonVo> pList = new ArrayList<PersonVo>();
		try {
			String query = "";
			query += " SELECT person_id, ";
			query += "        name, ";
			query += "        hp, ";
			query += "        company ";
			query += " FROM person ";
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo pv = new PersonVo(personId, name, hp, company);
				pList.add(pv);
			} // while
			/*
			 * for (int i = 0; i < pList.size(); i++) { PersonVo pv = pList.get(i);
			 * System.out.println( pv.getPersonId() + ".    " + pv.getName() + "    " +
			 * pv.getHp() + "    " + pv.getCompany()); }
			 */
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return pList;
	}// phoneSelect

	public void personSearch(String search) {
		getDriver();
		List<PersonVo> pList = new ArrayList<PersonVo>();
		try {
			String query = "";
			query += " SELECT person_id, ";
			query += "       name, ";
			query += "        hp, ";
			query += "        company ";
			query += " FROM person ";
			query += " WHERE name like ? ";
			query += " or hp like ? ";
			query += " or company like ? ";
			pstmt = conn.prepareStatement(query);

			String search01 = "%" + search + "%";
			pstmt.setString(1, search01);
			pstmt.setString(2, search01);
			pstmt.setString(3, search01);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int personId = rs.getInt("person_id");
				String personName = rs.getString("name");
				String personHp = rs.getString("hp");
				String personCpny = rs.getString("company");

				System.out.println(personId + "    " + personName + "    " + personHp + "    " + personCpny);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
