package com.example.stocks.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stocks.R;
import com.example.stocks.databinding.FragmentMarketBinding;
import com.example.stocks.ui.market.adapters.BondAdapter;
import com.example.stocks.ui.market.securities.Bond;
import com.example.stocks.ui.market.securities.Currency;

import java.sql.Array;
import java.util.Arrays;

public class MarketFragment extends Fragment {

    private View editProfileFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editProfileFragment = inflater.inflate(R.layout.fragment_market, container, false);

        ListView marketListview = editProfileFragment.findViewById(R.id.securitiesListView);

        Bond governmentBond = new Bond("Government Bond", Currency.RUB, 1, 1000.0, 60.0, 52.0, 0.9999);
        Bond largeCompanyBond = new Bond("Large Company Bond", Currency.RUB, 2, 5000.0, 450.0, 12.0, 0.9995);
        Bond municipalBond = new Bond("Municipal Bond", Currency.RUB, 3, 20000.0, 2400.0, 24.0, 0.999);
        Bond smallCompanyBond = new Bond("Small Company Bond", Currency.RUB, 4, 60000.0, 9000.0, 12.0, 0.997);

        final Bond[] bonds = new Bond[] {governmentBond, largeCompanyBond, municipalBond, smallCompanyBond};
        final String[] strings = new String[] {"1", "2"};
        BondAdapter adapter = new BondAdapter(getContext(), R.layout.list_bond_layout, R.id.list_item_bond_name, Arrays.asList(bonds));

        marketListview.setAdapter(adapter);

        return editProfileFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        editProfileFragment = null;
    }
}