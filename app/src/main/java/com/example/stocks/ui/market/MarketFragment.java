package com.example.stocks.ui.market;

import static com.example.stocks.Constants.updateInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.stocks.R;
import com.example.stocks.sqlite.UserCredentialsDatabaseHandler;
import com.example.stocks.ui.market.adapters.BondAdapter;
import com.example.stocks.ui.market.securities.Bond;
import com.example.stocks.ui.market.securities.Currency;

import java.util.Arrays;
import java.util.Map;

public class MarketFragment extends Fragment {

    private View editProfileFragment;
    private UserCredentialsDatabaseHandler mHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editProfileFragment = inflater.inflate(R.layout.fragment_market, container, false);

        TextView userBalance = editProfileFragment.findViewById(R.id.market_page_user_balance);
        TextView currentDate = editProfileFragment.findViewById(R.id.market_page_date);

        StringBuilder balancesString = new StringBuilder();

        mHandler = new UserCredentialsDatabaseHandler(getContext());

        int counter = 0;
        for (Map.Entry<String, Double> entry : mHandler.getUser().getBalances().entrySet()) {
            counter++;
            balancesString.append(entry.getValue()).append(" ").append(entry.getKey()).append(" / ");
            if (counter == 3) balancesString.append("\n");
        }
        balancesString = new StringBuilder(balancesString.substring(0, balancesString.length() - 2));

        userBalance.setText(balancesString.toString());
        currentDate.setText("loh");

        ListView marketListview = editProfileFragment.findViewById(R.id.securitiesListView);

        Bond governmentBond = new Bond("Government Bond", Currency.RUB, 1, 1000.0, 60.0, 52.0, 0.9999);
        Bond largeCompanyBond = new Bond("Large Company Bond", Currency.RUB, 2, 5000.0, 450.0, 12.0, 0.9995);
        Bond municipalBond = new Bond("Municipal Bond", Currency.RUB, 3, 20000.0, 2400.0, 24.0, 0.999);
        Bond smallCompanyBond = new Bond("Small Company Bond", Currency.RUB, 4, 60000.0, 9000.0, 12.0, 0.997);

        final Bond[] bonds = new Bond[] {governmentBond, largeCompanyBond, municipalBond, smallCompanyBond};
        BondAdapter adapter = new BondAdapter(getContext(), R.layout.list_bond_layout, R.id.list_item_bond_name, Arrays.asList(bonds));

        marketListview.setAdapter(adapter);

        return editProfileFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        editProfileFragment = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateInfo(mHandler);
    }
}