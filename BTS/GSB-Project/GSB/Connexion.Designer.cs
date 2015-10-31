using System.Drawing;
using System.Windows.Forms;
namespace GSB
{
    partial class Connexion
    {
        /// <summary>
        /// Variable nécessaire au concepteur.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Nettoyage des ressources utilisées.
        /// </summary>
        /// <param name="disposing">true si les ressources managées doivent être supprimées ; sinon, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Code généré par le Concepteur Windows Form

        /// <summary>
        /// Méthode requise pour la prise en charge du concepteur - ne modifiez pas
        /// le contenu de cette méthode avec l'éditeur de code.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Connexion));
            this.labelConnexion = new System.Windows.Forms.Label();
            this.textBoxLogin = new System.Windows.Forms.TextBox();
            this.labelTextLogin = new System.Windows.Forms.Label();
            this.labelTextPassword = new System.Windows.Forms.Label();
            this.textBoxPassword = new System.Windows.Forms.TextBox();
            this.buttonConnection = new System.Windows.Forms.Button();
            this.labelLoginError = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // labelConnexion
            // 
            this.labelConnexion.AutoSize = true;
            this.labelConnexion.Font = new System.Drawing.Font("Microsoft Sans Serif", 17F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelConnexion.Location = new System.Drawing.Point(73, 61);
            this.labelConnexion.Name = "labelConnexion";
            this.labelConnexion.Size = new System.Drawing.Size(326, 33);
            this.labelConnexion.TabIndex = 0;
            this.labelConnexion.Text = "Veuillez-vous connecter";
            // 
            // textBoxLogin
            // 
            this.textBoxLogin.ForeColor = System.Drawing.SystemColors.GrayText;
            this.textBoxLogin.Location = new System.Drawing.Point(199, 124);
            this.textBoxLogin.Name = "textBoxLogin";
            this.textBoxLogin.Size = new System.Drawing.Size(200, 22);
            this.textBoxLogin.TabIndex = 1;
            this.textBoxLogin.Text = "Login...";
            this.textBoxLogin.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textBoxLogin_KeyDown);
            this.textBoxLogin.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textBoxLogin_KeyUp);
            // 
            // labelTextLogin
            // 
            this.labelTextLogin.AutoSize = true;
            this.labelTextLogin.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelTextLogin.Location = new System.Drawing.Point(75, 126);
            this.labelTextLogin.Name = "labelTextLogin";
            this.labelTextLogin.Size = new System.Drawing.Size(50, 20);
            this.labelTextLogin.TabIndex = 2;
            this.labelTextLogin.Text = "Login";
            // 
            // labelTextPassword
            // 
            this.labelTextPassword.AutoSize = true;
            this.labelTextPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelTextPassword.Location = new System.Drawing.Point(75, 171);
            this.labelTextPassword.Name = "labelTextPassword";
            this.labelTextPassword.Size = new System.Drawing.Size(110, 20);
            this.labelTextPassword.TabIndex = 4;
            this.labelTextPassword.Text = "Mot de passe";
            // 
            // textBoxPassword
            // 
            this.textBoxPassword.ForeColor = System.Drawing.SystemColors.GrayText;
            this.textBoxPassword.Location = new System.Drawing.Point(199, 169);
            this.textBoxPassword.Name = "textBoxPassword";
            this.textBoxPassword.PasswordChar = '*';
            this.textBoxPassword.Size = new System.Drawing.Size(200, 22);
            this.textBoxPassword.TabIndex = 3;
            this.textBoxPassword.Text = "Password";
            this.textBoxPassword.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textBoxPassword_KeyDown);
            this.textBoxPassword.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textBoxPassword_KeyUp);
            // 
            // buttonConnection
            // 
            this.buttonConnection.Location = new System.Drawing.Point(300, 229);
            this.buttonConnection.Name = "buttonConnection";
            this.buttonConnection.Size = new System.Drawing.Size(99, 23);
            this.buttonConnection.TabIndex = 5;
            this.buttonConnection.Text = "CONNEXION";
            this.buttonConnection.UseVisualStyleBackColor = true;
            this.buttonConnection.Click += new System.EventHandler(this.buttonConnection_Click);
            // 
            // labelLoginError
            // 
            this.labelLoginError.AutoSize = true;
            this.labelLoginError.ForeColor = System.Drawing.Color.Red;
            this.labelLoginError.Location = new System.Drawing.Point(76, 211);
            this.labelLoginError.Name = "labelLoginError";
            this.labelLoginError.Size = new System.Drawing.Size(0, 17);
            this.labelLoginError.TabIndex = 6;
            this.labelLoginError.Visible = false;
            // 
            // Connexion
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(482, 303);
            this.Controls.Add(this.labelLoginError);
            this.Controls.Add(this.buttonConnection);
            this.Controls.Add(this.labelTextPassword);
            this.Controls.Add(this.textBoxPassword);
            this.Controls.Add(this.labelTextLogin);
            this.Controls.Add(this.textBoxLogin);
            this.Controls.Add(this.labelConnexion);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "Connexion";
            this.Text = "GSB - Connexion";
            this.ResumeLayout(false);
            this.PerformLayout();

        }


        #region Functions for TextBox Login
        private void textBoxLogin_KeyDown(object sender, KeyEventArgs e)
        {
            if (textBoxLogin.Text.Equals("Login...") == true)
            {
                textBoxLogin.Text = "";
                textBoxLogin.ForeColor = Color.Black;
            }
        }

        private void textBoxLogin_KeyUp(object sender, KeyEventArgs e)
        {
            if (textBoxLogin.Text.Equals(null) == true || textBoxLogin.Text.Equals("") == true)
            {
                textBoxLogin.Text = "Login...";
                textBoxLogin.ForeColor = Color.Gray;
            }
        }
        #endregion

        #region Functions for TextBox Password
        private void textBoxPassword_KeyDown(object sender, KeyEventArgs e)
        {
            if (textBoxPassword.Text.Equals("Password") == true)
            {
                textBoxPassword.Text = "";
                textBoxPassword.ForeColor = Color.Black;
            }
        }

        private void textBoxPassword_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                buttonConnection_Click(sender, e);
                e.Handled = true;
            }

            if (textBoxPassword.Text.Equals(null) == true || textBoxPassword.Text.Equals("") == true)
            {
                textBoxPassword.Text = "Password";
                textBoxPassword.ForeColor = Color.Gray;
            }
        }
        #endregion

        #endregion

        private System.Windows.Forms.Label labelConnexion;
        private System.Windows.Forms.TextBox textBoxLogin;
        private System.Windows.Forms.Label labelTextLogin;
        private System.Windows.Forms.Label labelTextPassword;
        private System.Windows.Forms.TextBox textBoxPassword;
        private System.Windows.Forms.Button buttonConnection;
        private Label labelLoginError;
    }
}

