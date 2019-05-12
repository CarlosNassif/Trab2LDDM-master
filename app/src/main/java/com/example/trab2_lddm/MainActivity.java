package com.example.trab2_lddm;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerInterface {

    RecyclerView meuRecyclerView;
    LinearLayoutManager meuLayoutManager;
    MeuAdapter adapter;
    MeuAdapter novoAdapter;
    MeuFragment meuFragment = new MeuFragment();
    private Node raiz = new Node();
    private Node pai = raiz;
    //private List<Node> nodes = new ArrayList<>();
    private List<Node> pais = null; //= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meuRecyclerView = findViewById(R.id.mainRV);
        meuLayoutManager = new LinearLayoutManager(this);
        meuRecyclerView.setLayoutManager(meuLayoutManager);
        adapter = new MeuAdapter(this,raiz.getChildren(),this);
        meuRecyclerView.setAdapter(adapter);
    }

    public void btnAdd(View V) {
        criaDialog();
    }

    public void btnRet(View V) {
        if(pais == null) {
            Toast.makeText(this, "Ou pera lá, da pra voltar mais não, rsrsrrs.", Toast.LENGTH_SHORT).show();
        } else {
            pai = pai.getFather();
            if (pai != raiz) {
                pais = pai.getFather().getChildren();
            } else {
                pais = null;
            }
            novoAdapter = new MeuAdapter(this, pai.getChildren(), this);
            meuRecyclerView.setAdapter(novoAdapter);
            adapter.notifyDataSetChanged();
            if (novoAdapter != null) novoAdapter.notifyDataSetChanged();
            Toast.makeText(this, "kakakakakakak lolololo", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnClose(View V) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(meuFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onItemClick(Object object) {
        Node node = (Node) object;
        meuFragment = MeuFragment.newFrag(node.getContent());
        if(node.isLeaf()){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container,meuFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            novoAdapter = new MeuAdapter(this,node.getChildren(),this);
            meuRecyclerView.setAdapter(novoAdapter);
            pais = pai.getChildren();
            pai = node;
            novoAdapter.notifyDataSetChanged();
        }
    }

    public void criaDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Node insertion");
        alert.setMessage("type node content");
        // Create TextView
        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // create node
                Node node = new Node(pai,input.getText().toString(), pai.getChildren().size());
                pai.getChildren().add(node);
                adapter.notifyDataSetChanged();
                if(novoAdapter != null) novoAdapter.notifyDataSetChanged();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                Toast.makeText(MainActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        });
        alert.show();
    }

}
