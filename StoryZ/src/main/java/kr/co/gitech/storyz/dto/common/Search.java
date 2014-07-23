package kr.co.gitech.storyz.dto.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 검색 조건
 * 
 * @author 김동택
 */
@JsonSerialize(include = Inclusion.NON_NULL)
@SuppressWarnings("serial")
public class Search extends BaseObject implements Cloneable {

	/**
	 * 검색 시작 위치 (0부터 시작)
	 */
	@NotEmpty
	private int pos = -1;

	/**
	 * 가져올 최대 개수. default: 20
	 */
	@NotEmpty
	private int max = 0;

	@NotEmpty
	private String sort = null;

	/**
	 * 시퀀스 기준으로 조회 default: 0
	 */
	@NotEmpty
	private int seq = 0;

	public Search() {
	}

	public Search clone() {
		Search search = null;
		try {
			search = (Search) super.clone();
			search.pos = pos;
			search.max = max;
			search.sort = sort;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return search;
	}

	/**
	 * @return the pos
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * @param pos
	 *            the pos to set
	 */
	public void setPos(int pos) {
		this.pos = pos;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	public int getMax(int defVal) {
		if (max <= 0)
			return defVal;
		else
			return max;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * sort 가 빈 값(null, "")이면 defVal 값으로 설정후 리턴
	 * 
	 * @param sort
	 * @param defVal
	 */
	public String getSort(String defVal) {
		setSortWithDef(defVal);
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * sort 가 빈 값(null, "")이면 defVal 값으로 설정
	 * 
	 * @param sort
	 * @param defVal
	 */
	public void setSortWithDef(String defVal) {
		if (sort == null || "".equals(sort))
			this.setSort(defVal);
	}

	public final String buildString() {
		return String.format("pos <%d>, max <%d>, sort <%s>", pos, max, sort);
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

}
