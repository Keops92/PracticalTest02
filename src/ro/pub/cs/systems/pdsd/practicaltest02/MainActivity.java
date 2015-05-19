package ro.pub.cs.systems.pdsd.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.practicaltest02.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private ServerThread server;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EditText serverPort = (EditText)findViewById(R.id.port);
		serverPort.setText(new Integer(Constants.SERVER_PORT).toString());
		server = new ServerThread();
		server.startServer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void AddButtonHandler(View v) {
		new SendInfo(R.id.addResult, "add").execute();
	}
	
	public void MulButtonHandler(View v) {
		new SendInfo(R.id.mulResult, "mul").execute();
	}
	
	class SendInfo extends AsyncTask<String, Integer, String> {
		String op1;
		String op2;
		int updateId;
		String opCode;
		
		public SendInfo(int updateId, String opcode) {
			this.updateId = updateId;
			this.opCode = opcode;
			EditText op1 = (EditText)findViewById(R.id.op1);
			this.op1 = op1.getText().toString();
			EditText op2 = (EditText)findViewById(R.id.op2);
			this.op2 = op2.getText().toString();
			
		}
		
		@Override
		protected String doInBackground(String... params) {
			try {
				Socket serverSocket = new Socket(
							Constants.SERVER_HOST,
							Constants.SERVER_PORT
						);
				String op = this.opCode+","+this.op1+","+this.op2;
				PrintWriter out =
				        new PrintWriter(serverSocket.getOutputStream(), true);
				out.println(op);
				BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
				return in.readLine();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		public void onPostExecute(String result){
			EditText resultObj = (EditText)findViewById(this.updateId);
			resultObj.setText(result);
		}
		
	}
}
