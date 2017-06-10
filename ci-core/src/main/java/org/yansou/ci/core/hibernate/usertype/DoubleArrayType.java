package org.yansou.ci.core.hibernate.usertype;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoubleArrayType implements Serializable, UserType {

	private static final Logger LOG = LogManager.getLogger(DoubleArrayType.class);

	private final Gson gson = new Gson();

	@Override
	public int[] sqlTypes() {
		return new int[]{StringType.INSTANCE.sqlType()};
	}

	@Override
	public Class<?> returnedClass() {
		return String[].class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {

		return false;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {

		return 0;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws
			HibernateException, SQLException {
		try {
			String value = StringType.INSTANCE.nullSafeGet(rs, names[0], session);

			if (value != null) {
				Double[] array = gson.fromJson(value, Double[].class);

				return array;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws
			HibernateException, SQLException {
		String str = null;
		try {
			if (value != null) {
				str = gson.toJson(value);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		StringType.INSTANCE.nullSafeSet(st, str, index, session);
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {

		return null;
	}

	@Override
	public boolean isMutable() {

		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {

		return null;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {

		return null;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {

		return null;
	}

}
