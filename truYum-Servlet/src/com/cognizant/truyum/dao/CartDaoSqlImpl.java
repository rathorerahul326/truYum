package com.cognizant.truyum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.model.MenuItem;

public class CartDaoSqlImpl implements CartDao{
	public double price=0;
	@Override
	public void addCartItem(long userId, long menuItemId) {
		// TODO Auto-generated method stub
		try {
		Connection con=ConnectionHandler.getConnection();
		
			PreparedStatement stmt=con.prepareStatement("insert into cart (ct_us_id,ct_pr_id) values('"+userId+"','"+menuItemId+"')");
			stmt.executeUpdate();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}

	@Override
	public ArrayList<MenuItem> getAllCartItems(long userId) throws CartEmptyException {
		// TODO Auto-generated method stub
		Connection con=ConnectionHandler.getConnection();
		
		ArrayList<MenuItem> menuItemList=new ArrayList<MenuItem>();
		try {
		MenuItem menuItem=null;
		
		
			PreparedStatement stmt = con.prepareStatement("select * from menu_item inner join cart on cart.ct_pr_id=menu_item.me_id");
			ResultSet rs=stmt.executeQuery(); 
			while(rs.next()){
				
				//MenuItem menuItem=new MenuItem(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5));  
				
				 
				
			
				String active=rs.getString(4);
				//System.out.println(active);
				boolean b=true;
				if(active.equals("Yes")){
					b=true;
					
				}
				if(active.equals("No")){
					b=false;
					
				}
				 price=rs.getFloat(3)+price;
				menuItem=new MenuItem((long)rs.getInt(1),rs.getString(2),rs.getFloat(3),b,rs.getDate(5),rs.getString(6),rs.getBoolean(7));
				System.out.println(menuItem);
				System.out.println(price);
				menuItemList.add(menuItem);
				
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Cart c=new Cart(menuItemList,price);
		return menuItemList;
	}

	@Override
	public void removeCartItem(long userId, long menuItemId) {
		// TODO Auto-generated method stub
		Connection con=ConnectionHandler.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("delete from cart where ct_us_id='"+userId+"' and ct_pr_id='"+menuItemId+"' limit 1");
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
