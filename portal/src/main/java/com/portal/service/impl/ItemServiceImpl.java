package com.portal.service.impl;

import com.common.utils.JsonUtils;
import com.mapperutils.entity.TbItem;
import com.mapperutils.entity.TbItemDesc;
import com.mapperutils.entity.TbItemParamItem;
import com.portal.feign.ItemFeignService;
import com.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemFeignService itemFeignService;

	@Override
	public TbItem itemInfo(Long itemId) {
		return itemFeignService.getItemInfo(itemId);
	}

	@Override
	public TbItemDesc showItemDesc(Long itemId) {
		return itemFeignService.getItemDesc(itemId);
	}

	@Override
	public String showItemParam(Long itemId) {
		try{
			TbItemParamItem paramItem = itemFeignService.getItemParam(itemId);
			String paramData = paramItem.getParamData();
			if(paramData != null){
				List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);

				//构造html片段
				StringBuffer sb = new StringBuffer();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
				sb.append("    <tbody>\n");
				for(Map m1:jsonList) {
					sb.append("        <tr>\n");
					sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
					sb.append("        </tr>\n");
					List<Map> list2 = (List<Map>) m1.get("params");
					for(Map m2:list2) {
						sb.append("        <tr>\n");
						sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
						sb.append("            <td>"+m2.get("v")+"</td>\n");
						sb.append("        </tr>\n");
					}
				}
				sb.append("    </tbody>\n");
				sb.append("</table>");
				//返回html片段
				return sb.toString();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
