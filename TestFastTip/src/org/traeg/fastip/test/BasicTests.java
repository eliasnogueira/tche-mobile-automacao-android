package org.traeg.fastip.test;

import org.traeg.fastip.MainActivity;
import org.traeg.fastip.R;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

/*
 * Classe de teste deve estender ActivityInstrumentationTestCase2 tipando com a atividade principal da app
 */
public class BasicTests extends ActivityInstrumentationTestCase2<MainActivity> {

	/*
	 * Declaraçã do objeto para automação (Robotium)
	 */
	private Solo solo;

	/*
	 * Devemos ter um construtor com um super para a atividade principal da app
	 */
	public BasicTests() {
		super(MainActivity.class);
	}

	/*
	 * A pré-condição para todos os testes é criar a instância do Robotium recebendo a instrumentação e atividade principal
	 */
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	/*
	 * A pós-condição é fechar todas as activities correntes
	 */
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}

	/*
	 * Teste tipo white box validando se o texto é apresentado na tela
	 */
	public void testCalculaTip_WhiteBox() throws InterruptedException {
		EditText billAmount = (EditText) solo.getView(R.id.billAmtEditText);

		solo.enterText(billAmount, "15");
		solo.clickOnButton("Calculate Tip");

		assertTrue("Valor da gorjeta não apresentado", solo.searchText("2.25"));
		assertTrue("Valor total não apresentado", solo.searchText("17.25"));
	}

	/*
	 * Teste tipo white box validando exatamente o valor do texto pelo seu componente
	 */
	public void testCaculaTip_WhiteBox2() {
		EditText billAmount = (EditText) solo.getView(R.id.billAmtEditText);
		final TextView tipAmount = (TextView) solo.getView(R.id.tipAmtTextView);
		TextView totalAmount = (TextView) solo.getView(R.id.totalAmtTextView);
		
		solo.enterText(billAmount, "15");
		solo.clickOnButton("Calculate Tip");
		
		solo.waitForCondition(new Condition() {
			@Override
			public boolean isSatisfied() {
				return !tipAmount.getText().toString().isEmpty();
			}
		}, 10);
		
		assertEquals("$2.25", tipAmount.getText().toString());
		assertEquals("$17.25", totalAmount.getText().toString());
	}
	
	
	/*
	 * Teste tipo black box validando se o texto é apresentado na tela
	 * Quando temos um tipo blackbox estamos utilizando o numero referente a apresentacao do elemento, sequencialemtne.
	 * Ex: a primeira caixa de texto (textview) apresentada é a zero, a segunda é a 1 e assim por diante
	 */
	public void testCalculaTip_BlackBox() {
		solo.enterText(0, "15");
		solo.clickOnButton("Calculate Tip");

		assertTrue("Valor da gorjeta não apresentado", solo.searchText("2.25"));
		assertTrue("Valor total não apresentado", solo.searchText("17.25"));
	}
	
	/*
	 * Teste mostrando como trabalhar com uma actionbar
	 */
	public void testAltaraPercentual_WhiteBox() {
		solo.clickOnActionBarItem(R.id.menu_settings);
		solo.enterText(0, "20");
		
		solo.enterText(0, "");
		solo.enterText(0, "20");
		solo.clickOnButton("Save Settings");
		
		solo.enterText(0, "15");
		solo.clickOnButton("Calculate Tip");
		assertTrue(solo.searchText("3.00"));
		assertTrue(solo.searchText("18.00"));
	}

}
