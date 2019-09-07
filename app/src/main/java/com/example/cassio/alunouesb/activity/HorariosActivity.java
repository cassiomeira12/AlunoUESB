package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.model.Disciplina;
import com.example.cassio.alunouesb.model.Horario;
import com.example.cassio.alunouesb.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class HorariosActivity extends AppCompatActivity {

    private Usuario usuario = PrincipalActivity.usuario;
    private ListView listDisciplinas;
    private ArrayList<Disciplina> disciplinaList;
    private List<Horario> horarioList;

    private Button buttonSeg1;
    private Button buttonSeg2;
    private Button buttonSeg3;

    private Button buttonTer1;
    private Button buttonTer2;
    private Button buttonTer3;

    private Button buttonQuar1;
    private Button buttonQuar2;
    private Button buttonQuar3;

    private Button buttonQuin1;
    private Button buttonQuin2;
    private Button buttonQuin3;

    private Button buttonSex1;
    private Button buttonSex2;
    private Button buttonSex3;

    private Button buttonSab1;
    private Button buttonSab2;
    private Button buttonSab3;

    private int REQUEST_ABRIR_DISCIPLINA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);
        setTitle("Meus Horários");

        listDisciplinas = findViewById(R.id.list_disciplinas);
        disciplinaList = (ArrayList<Disciplina>) usuario.getSemestreList().get(usuario.getIdSemestre()).getDisciplinaList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, configurarListView(disciplinaList));

        listDisciplinas.setAdapter(adapter);

        configurarButtons();
        adicionarDisciplina();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Disciplina disciplina;

        //Caso editou um Item
//        if (requestCode == REQUEST_ABRIR_DISCIPLINA && resultCode == 1) {
//            //disciplina = (Disciplina) data.getSerializableExtra("disciplina");
//            disciplinaList = DisciplinaDAO.getInstance(this).buscarTodos(PrincipalActivity.usuario.getIdSemestre());
//            adicionarDisciplina();
//        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void chamarTelaDisciplina(View view) {

        if (view.getTag() != null) {
            Intent intent = new Intent(this, DisciplinaActivity.class);
            intent.putExtra("disciplina", ((Disciplina) view.getTag()));
            startActivityForResult(intent, REQUEST_ABRIR_DISCIPLINA);
        }

    }

    private void configurarButtons() {

        buttonSeg1 = (Button) findViewById(R.id.button_seg_1);
        buttonSeg2 = (Button) findViewById(R.id.button_seg_2);
        buttonSeg3 = (Button) findViewById(R.id.button_seg_3);

        /*buttonSeg1.setVisibility(View.INVISIBLE);
        buttonSeg2.setVisibility(View.INVISIBLE);
        buttonSeg3.setVisibility(View.INVISIBLE);*/

        buttonTer1 = (Button) findViewById(R.id.button_ter_1);
        buttonTer2 = (Button) findViewById(R.id.button_ter_2);
        buttonTer3 = (Button) findViewById(R.id.button_ter_3);

        /*buttonTer1.setVisibility(View.INVISIBLE);
        buttonTer2.setVisibility(View.INVISIBLE);
        buttonTer3.setVisibility(View.INVISIBLE);
*/
        buttonQuar1 = (Button) findViewById(R.id.button_quar_1);
        buttonQuar2 = (Button) findViewById(R.id.button_quar_2);
        buttonQuar3 = (Button) findViewById(R.id.button_quar_3);

        /*buttonQuar1.setVisibility(View.INVISIBLE);
        buttonQuar2.setVisibility(View.INVISIBLE);
        buttonQuar3.setVisibility(View.INVISIBLE);*/

        buttonQuin1 = (Button) findViewById(R.id.button_quin_1);
        buttonQuin2 = (Button) findViewById(R.id.button_quin_2);
        buttonQuin3 = (Button) findViewById(R.id.button_quin_3);

        /*buttonQuin1.setVisibility(View.INVISIBLE);
        buttonQuin2.setVisibility(View.INVISIBLE);
        buttonQuin3.setVisibility(View.INVISIBLE);*/

        buttonSex1 = (Button) findViewById(R.id.button_sex_1);
        buttonSex2 = (Button) findViewById(R.id.button_sex_2);
        buttonSex3 = (Button) findViewById(R.id.button_sex_3);

        /*buttonSex1.setVisibility(View.INVISIBLE);
        buttonSex2.setVisibility(View.INVISIBLE);
        buttonSex3.setVisibility(View.INVISIBLE);*/

        buttonSab1 = (Button) findViewById(R.id.button_sab_1);
        buttonSab2 = (Button) findViewById(R.id.button_sab_2);
        buttonSab3 = (Button) findViewById(R.id.button_sab_3);

        //buttonSab1.setEnabled(false);
        //buttonSab2.setVisibility(View.INVISIBLE);
        //buttonSab3.setVisibility(View.INVISIBLE);
    }

    private void adicionarDisciplina() {
        for (Disciplina disciplina : disciplinaList) {
            for (Horario horario : disciplina.getHorarioList()) {
                this.setHorario(horario);
            }
        }

    }

    private void setHorario(Horario horario) {

//        switch (horario.getDia()) {
//
//            case 0://Segunda
//
//                if (horario.getHorario() == 0) {
//                    this.buttonSeg1.setText(disciplina.getAbreviacao());
//                    this.buttonSeg1.setTag(disciplina);
//                    this.buttonSeg1.setEnabled(true);
//                } else if (horario.getHorario() == 1) {
//                    this.buttonSeg2.setText(disciplina.getAbreviacao());
//                    this.buttonSeg2.setTag(disciplina);
//                    this.buttonSeg2.setEnabled(true);
//                } else {
//                    this.buttonSeg3.setText(disciplina.getAbreviacao());
//                    this.buttonSeg3.setTag(disciplina);
//                    this.buttonSeg3.setEnabled(true);
//                }
//                break;
//
//            case 1://Terça
//
//                if (horario.getHorario() == 0) {
//                    this.buttonTer1.setText(disciplina.getAbreviacao());
//                    this.buttonTer1.setTag(disciplina);
//                    this.buttonTer1.setEnabled(true);
//                } else if (horario.getHorario() == 1) {
//                    this.buttonTer2.setText(disciplina.getAbreviacao());
//                    this.buttonTer2.setTag(disciplina);
//                    this.buttonTer2.setEnabled(true);
//                } else {
//                    this.buttonTer3.setText(disciplina.getAbreviacao());
//                    this.buttonTer3.setTag(disciplina);
//                    this.buttonTer3.setEnabled(true);
//                }
//                break;
//
//            case 2://Quarta
//
//                if (horario.getHorario() == 0) {
//                    this.buttonQuar1.setText(disciplina.getAbreviacao());
//                    this.buttonQuar1.setTag(disciplina);
//                    this.buttonQuar1.setEnabled(true);
//                } else if (horario.getHorario() == 1) {
//                    this.buttonQuar2.setText(disciplina.getAbreviacao());
//                    this.buttonQuar2.setTag(disciplina);
//                    this.buttonQuar2.setEnabled(true);
//                } else {
//                    this.buttonQuar3.setText(disciplina.getAbreviacao());
//                    this.buttonQuar3.setTag(disciplina);
//                    this.buttonQuar3.setEnabled(true);
//                }
//                break;
//
//            case 3://Quinta
//
//                if (horario.getHorario() == 0) {
//                    this.buttonQuin1.setText(disciplina.getAbreviacao());
//                    this.buttonQuin1.setTag(disciplina);
//                    this.buttonQuin1.setEnabled(true);
//                } else if (horario.getHorario() == 1) {
//                    this.buttonQuin2.setText(disciplina.getAbreviacao());
//                    this.buttonQuin2.setTag(disciplina);
//                    this.buttonQuin2.setEnabled(true);
//                } else {
//                    this.buttonQuin3.setText(disciplina.getAbreviacao());
//                    this.buttonQuin3.setTag(disciplina);
//                    this.buttonQuin3.setEnabled(true);
//                }
//                break;
//
//            case 4://Sexta
//
//                if (horario.getHorario() == 0) {
//                    this.buttonSex1.setText(disciplina.getAbreviacao());
//                    this.buttonSex1.setTag(disciplina);
//                    this.buttonSex1.setEnabled(true);
//                } else if (horario.getHorario() == 1) {
//                    this.buttonSex2.setText(disciplina.getAbreviacao());
//                    this.buttonSex2.setTag(disciplina);
//                    this.buttonSex2.setEnabled(true);
//                } else {
//                    this.buttonSex3.setText(disciplina.getAbreviacao());
//                    this.buttonSex3.setTag(disciplina);
//                    this.buttonSex3.setEnabled(true);
//                }
//                break;
//
//            case 5://Sabado
//
//                if (horario.getHorario() == 0) {
//                    this.buttonSab1.setText(disciplina.getAbreviacao());
//                    this.buttonSab1.setTag(disciplina);
//                    this.buttonSab1.setEnabled(true);
//                } else if (horario.getHorario() == 1) {
//                    this.buttonSab2.setText(disciplina.getAbreviacao());
//                    this.buttonSab2.setTag(disciplina);
//                    this.buttonSab2.setEnabled(true);
//                } else {
//                    this.buttonSab3.setText(disciplina.getAbreviacao());
//                    this.buttonSab3.setTag(disciplina);
//                    this.buttonSab3.setEnabled(true);
//                }
//                break;
//
//        }
    }

    private List<String> configurarListView(List<Disciplina> lista) {
        List<String> resultado = new ArrayList<>();

        for (Disciplina disciplina : lista) {
            resultado.add(disciplina.getAbreviacao() + "\t\t-\t\t" + disciplina.getNome());
        }

        return resultado;
    }


}
