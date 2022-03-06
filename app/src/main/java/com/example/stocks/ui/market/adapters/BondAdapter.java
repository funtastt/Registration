package com.example.stocks.ui.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stocks.R;
import com.example.stocks.ui.market.securities.Bond;

import java.util.List;

public class BondAdapter extends ArrayAdapter<Bond> {

    public BondAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Bond> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public BondAdapter(@NonNull Context context, int resource, @NonNull Bond[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Bond bond = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_bond_layout, parent, false);
        }

        TextView bondName = convertView.findViewById(R.id.list_item_bond_name);
        TextView bondDate = convertView.findViewById(R.id.list_item_bond_date);
        TextView bondPrice = convertView.findViewById(R.id.list_item_bond_price);
        TextView bondPriceCurrency = convertView.findViewById(R.id.list_item_bond_price_currency);
        TextView bondCoupon = convertView.findViewById(R.id.list_item_bond_coupon);
        TextView bondCouponCurrency = convertView.findViewById(R.id.list_item_bond_coupon_currency);

        bondName.setText(bond.getName());
        // Todo: implement logic
        bondDate.setText("Maturity of the bond: " + bond.getPaymentPeriod());
        bondPrice.setText(String.valueOf(bond.getCurrentPrice()));
        bondPriceCurrency.setText(bond.getCurrency().getCurrencySymbol());
        bondCoupon.setText(String.valueOf(bond.getCouponPrice()));
        bondCouponCurrency.setText(bond.getCurrency().getCurrencySymbol());
        return super.getView(position, convertView, parent);
    }
}
