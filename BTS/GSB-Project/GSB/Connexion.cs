using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace GSB
{
    public partial class Connexion : Form
    {
        /// <summary>
        /// Constructor
        /// </summary>
        public Connexion()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Function for login
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        private void buttonConnection_Click(object sender, EventArgs e)
        {
            //Set cursor
            Cursor.Current = Cursors.WaitCursor;

            if (labelLoginError.Visible)
            {
                labelLoginError.Visible = false;
                labelLoginError.Text = "";
            }

            //Check if text in TextBox Login/Password is not empty and not equals of placeholder
            if (!textBoxLogin.Text.Equals("") && !textBoxLogin.Text.Equals("Login...")
                && !textBoxPassword.Text.Equals("") && !textBoxPassword.Text.Equals("Password"))
            {

                var user = Functions.getUser(textBoxLogin.Text, textBoxPassword.Text);
                if (user is string)
                {
                    showError((string)user);
                }
                else
                {
                    //Check if user is comptable or not
                    visiteur currentUser = (GSB.visiteur)user;
                    if (currentUser.isComptable == 1)
                    {
                        Comptable c = new Comptable(currentUser);
                        this.Hide();
                        c.ShowDialog();
                        this.Close();
                    }
                    else
                    {
                        Gestion g = new Gestion(currentUser);
                        this.Hide();
                        g.ShowDialog();
                        this.Close();
                    }
                }
            }
            else
            {
                showError("Veuillez entrer un login et un mot de passe!");
            }
        }

        /// <summary>
        /// Function for close form
        /// </summary>
        /// <param name="sender"> Object </param>
        /// <param name="e"> Events </param>
        void g_FormClosed(object sender, FormClosedEventArgs e)
        {
            this.Close();
        }

        /// <summary>
        /// Function for show error
        /// </summary>
        /// <param name="text"> Text to Show </param>
        private void showError(String text)
        {
            labelLoginError.Text = text;
            labelLoginError.Visible = true;
        }
    }
}
