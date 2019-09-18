package com.cognizant.truyum.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.model.MenuItem;

public class CartDaoCollectionImpl implements CartDao 
{
	private static HashMap<Long,Cart> userCarts;
 
 
	public static HashMap<Long, Cart> getUserCarts() {
		return userCarts;
	}

	public static void setUserCarts(HashMap<Long, Cart> userCarts) {
		CartDaoCollectionImpl.userCarts = userCarts;
	}

	public CartDaoCollectionImpl() 
	{
		super();
		if(userCarts == null) {
		userCarts = new HashMap<Long, Cart>();
	}
}

	@Override
	public void addCartItem(long userId, long menuItemId)
	{
		try 
		{
			MenuItemDao menuItemDao = new MenuItemDaoCollectionImpl();
			MenuItem menuItem =menuItemDao.getMenuItem(menuItemId);
			if(userCarts.containsKey(userId)) 
			{
				ArrayList<MenuItem> cartItems= userCarts.get(userId).getMenuItems();
				cartItems.add(menuItem);
				userCarts.get(userId).setMenuItems(cartItems);
			}
			else
			{
				ArrayList<MenuItem> cartMenu= new ArrayList<MenuItem>();
				cartMenu.add(menuItem);
				Cart newCart= new Cart(cartMenu,0);
				newCart.setTotal(0);
				userCarts.put(userId, newCart);
				
			}
		} 
		catch (Exception e) 
		{
						e.printStackTrace();
		}
		
	}
	

	@Override
	public void removeCartItem(long userId, long menuItemId) 
	{
		ArrayList<MenuItem> userItem = new ArrayList<MenuItem>();
		userItem = userCarts.get(userId).getMenuItems();
		for(MenuItem item : userItem) 
		{
			if(item.getId() == menuItemId)
			{
				userItem.remove(userItem.indexOf(item));
				break;
			}
		}
		userCarts.get(userId).setMenuItems(userItem);
	}

	@Override
	public ArrayList<MenuItem> getAllCartItems(long userId) throws CartEmptyException 
	{
		ArrayList<MenuItem> menuItemList = new ArrayList<MenuItem>();
		menuItemList = userCarts.get(userId).getMenuItems();
		
		if(menuItemList.isEmpty()) {
			throw new CartEmptyException();
		}
		else 
		{
			double total =0;
			for(MenuItem item : menuItemList) 
			{
				total+=item.getPrice();
			}
			userCarts.get(userId).setTotal(total);
			}
		
			return menuItemList;
		}
	}