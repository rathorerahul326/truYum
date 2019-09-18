package com.cognizant.truyum.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.util.DateUtil;

public class MenuItemDaoCollectionImpl implements MenuItemDao
{
	private static ArrayList<MenuItem> menuItemList;

	public MenuItemDaoCollectionImpl() throws Exception 
	{
		super();
		if (menuItemList == null) 
		{
			menuItemList = new ArrayList<MenuItem>();
			menuItemList.add(new MenuItem((long) 01, "Sandwich", (float) 99.0, true,DateUtil.convertToDate("15/03/2017"), "Main Course", true));
			menuItemList.add(new MenuItem((long) 02, "Burger", (float) 129.0, true,DateUtil.convertToDate("23/12/2027"), "Main Course", false));
			menuItemList.add(new MenuItem((long) 03, "Pizza", (float) 149.0, true, DateUtil.convertToDate("21/08/2018"),"Main Course", false));
			menuItemList.add(new MenuItem((long) 04, "French Fries", (float) 57.0, false,DateUtil.convertToDate("02/07/2017"), "Starters", true));
			menuItemList.add(new MenuItem((long) 05, "Chocolate Brownie", (float) 32.0, true,DateUtil.convertToDate("02/11/2022"), "Dessert", true));
		}
	}

	@Override
	public ArrayList<MenuItem> getMenuItemListAdmin()
	{
		
		return menuItemList;
	}

	@Override
	public ArrayList<MenuItem> getMenuItemListCustomer()
	{
		ArrayList<MenuItem> menuItemListCustomer = new ArrayList<MenuItem>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String today = simpleDateFormat.format(date);
		try {
			Date Today = DateUtil.convertToDate(today);
			for (MenuItem items : menuItemList)
			{
				if (Today.compareTo(items.getDateOfLaunch()) < 0 && items.isActive() == true)
				{
					menuItemListCustomer.add(items);
				}
			}
			return menuItemListCustomer;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}

		return menuItemListCustomer;
	}

	@Override
	public void modifyMenuItem(MenuItem menuItem)
	{
		for(MenuItem item : menuItemList)
		{
			if(item.equals(menuItem))
			{
				item.setActive(menuItem.isActive());
				item.setCategory(menuItem.getCategory());
				item.setDateOfLaunch(menuItem.getDateOfLaunch());
				item.setFreeDelivery(menuItem.isFreeDelivery());
				item.setName(menuItem.getName());
				item.setPrice(menuItem.getPrice()); 
				
			}
		}

	}

	@Override
	public MenuItem getMenuItem(long menuItemId)
	{
		for(MenuItem menuItem : menuItemList)
		{
			if(menuItem.getId() == menuItemId)
			{
				return menuItem;
			}
		}
		
		return null;
	}

}