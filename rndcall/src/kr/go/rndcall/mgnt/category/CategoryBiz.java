package kr.go.rndcall.mgnt.category;

import kr.go.rndcall.mgnt.category.CategoryDAO;
import kr.go.rndcall.mgnt.category.CategoryResultVO;
import kr.go.rndcall.mgnt.category.CategorySearchVO;

public class CategoryBiz {

	public CategoryResultVO getCategoryList(CategorySearchVO searchVO, CategoryVO vo) throws Exception {
		CategoryResultVO resultVO = new CategoryResultVO();
		CategoryDAO dao = new CategoryDAO();
		
//		if (searchVO.getCrud().equals("INSERT")) {
//			dao.insert(vo);
//		} else if (searchVO.getCrud().equals("UPDATE")) {
//			dao.update(vo);
//		} else if (searchVO.getCrud().equals("DELETE")) {
		if (searchVO.getCrud().equals("DELETE")) {
			dao.delete(vo);
		}
		searchVO.setCrud("");
		
		resultVO = dao.getCategoryList(searchVO);
		
		return resultVO;
	}

	public CategoryResultVO getCategoryListDtl(CategorySearchVO searchVO, CategoryVO vo) throws Exception {
		CategoryResultVO resultVO = new CategoryResultVO();
		CategoryDAO dao = new CategoryDAO();
		
		if (searchVO.getCrud().equals("INSERT")) {
			dao.insert(vo);
		} else if (searchVO.getCrud().equals("UPDATE")) {
			dao.update(vo);
		} else if (searchVO.getCrud().equals("DELETE")) {
			dao.delete(vo);
		}
		searchVO.setCrud("");
		
		resultVO = dao.getCategoryListDtl(searchVO);
		
		return resultVO;
	}

	public CategoryResultVO getCategoryInfo(CategorySearchVO searchVO, CategoryVO vo) throws Exception {
		CategoryResultVO resultVO = new CategoryResultVO();
		CategoryDAO dao = new CategoryDAO();
		String result = "";
		
		if (searchVO.getCrud().equals("UPDATE")) {
			result = dao.update(vo);
		}
		resultVO = dao.getCategoryInfo(searchVO);
		resultVO.setErrCd(result);
		
		return resultVO;
	}

	public CategoryResultVO getCategoryInsert(CategorySearchVO searchVO, CategoryVO vo) throws Exception {
	    CategoryResultVO result = new CategoryResultVO();
	    CategoryDAO dao = new CategoryDAO();
	    
	    if (searchVO.getCrud().equals("INSERT")) {
	        dao.insert(vo);
	        result.setErrCd("INSERT");
	    }
	    return result;
	}

}
