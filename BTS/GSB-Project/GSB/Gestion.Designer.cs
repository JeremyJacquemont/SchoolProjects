namespace GSB
{
    partial class Gestion
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Gestion));
            this.buttonLogout = new System.Windows.Forms.Button();
            this.labelLogout = new System.Windows.Forms.Label();
            this.labelQuestion = new System.Windows.Forms.Label();
            this.buttonView = new System.Windows.Forms.Button();
            this.buttonAdd = new System.Windows.Forms.Button();
            this.panelView = new System.Windows.Forms.Panel();
            this.panelComboView = new System.Windows.Forms.Panel();
            this.textBoxComboDate = new System.Windows.Forms.TextBox();
            this.dataGridViewComboNonForfaitise = new System.Windows.Forms.DataGridView();
            this.labelNonForfaitiseCombo = new System.Windows.Forms.Label();
            this.labelForfaitiseCombo = new System.Windows.Forms.Label();
            this.dataGridViewComboForfaitise = new System.Windows.Forms.DataGridView();
            this.labelComboDate = new System.Windows.Forms.Label();
            this.textBoxCombo = new System.Windows.Forms.TextBox();
            this.labelComboEtat = new System.Windows.Forms.Label();
            this.buttonComboView = new System.Windows.Forms.Button();
            this.comboBoxView = new System.Windows.Forms.ComboBox();
            this.labelView = new System.Windows.Forms.Label();
            this.panelAdd = new System.Windows.Forms.Panel();
            this.labelAddFicheHorsFraisDataGridView = new System.Windows.Forms.DataGridView();
            this.labelAddFicheHorsFraisButton = new System.Windows.Forms.Button();
            this.labelAddFicheHorsFraisDateTimePicker = new System.Windows.Forms.DateTimePicker();
            this.labelAddFicheHorsFraisNumericUpDown = new System.Windows.Forms.NumericUpDown();
            this.labelAddFicheHorsFraisTextBox = new System.Windows.Forms.TextBox();
            this.labelAddFicheHorsFraisDate = new System.Windows.Forms.Label();
            this.labelAddFicheHorsFraisMontant = new System.Windows.Forms.Label();
            this.labelAddFicheHorsFraisLibelle = new System.Windows.Forms.Label();
            this.labelAddFicheHorsFrais = new System.Windows.Forms.Label();
            this.labelAddFicheFraisDataGridView = new System.Windows.Forms.DataGridView();
            this.FicheFraisAddValid = new System.Windows.Forms.Button();
            this.FicheFraisNumericUpDown = new System.Windows.Forms.NumericUpDown();
            this.labelAddFicheFraisQuantity = new System.Windows.Forms.Label();
            this.labelAddFicheFraisComboBox = new System.Windows.Forms.ComboBox();
            this.labelAddFicheFraisChoice = new System.Windows.Forms.Label();
            this.labelAddFicheFrais = new System.Windows.Forms.Label();
            this.panelView.SuspendLayout();
            this.panelComboView.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewComboNonForfaitise)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewComboForfaitise)).BeginInit();
            this.panelAdd.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.labelAddFicheHorsFraisDataGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.labelAddFicheHorsFraisNumericUpDown)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.labelAddFicheFraisDataGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.FicheFraisNumericUpDown)).BeginInit();
            this.SuspendLayout();
            // 
            // buttonLogout
            // 
            this.buttonLogout.Location = new System.Drawing.Point(868, 12);
            this.buttonLogout.Name = "buttonLogout";
            this.buttonLogout.Size = new System.Drawing.Size(126, 23);
            this.buttonLogout.TabIndex = 0;
            this.buttonLogout.Text = "DECONNECTION";
            this.buttonLogout.UseVisualStyleBackColor = true;
            this.buttonLogout.Click += new System.EventHandler(this.buttonLogout_Click);
            // 
            // labelLogout
            // 
            this.labelLogout.AutoSize = true;
            this.labelLogout.Location = new System.Drawing.Point(12, 18);
            this.labelLogout.Name = "labelLogout";
            this.labelLogout.Size = new System.Drawing.Size(0, 17);
            this.labelLogout.TabIndex = 1;
            // 
            // labelQuestion
            // 
            this.labelQuestion.AutoSize = true;
            this.labelQuestion.Location = new System.Drawing.Point(15, 71);
            this.labelQuestion.Name = "labelQuestion";
            this.labelQuestion.Size = new System.Drawing.Size(155, 17);
            this.labelQuestion.TabIndex = 2;
            this.labelQuestion.Text = "Que voulez-vous faire?";
            // 
            // buttonView
            // 
            this.buttonView.Location = new System.Drawing.Point(18, 100);
            this.buttonView.Name = "buttonView";
            this.buttonView.Size = new System.Drawing.Size(211, 31);
            this.buttonView.TabIndex = 3;
            this.buttonView.Text = "Voir les fiches précédentes";
            this.buttonView.UseVisualStyleBackColor = true;
            this.buttonView.Click += new System.EventHandler(this.buttonView_Click);
            // 
            // buttonAdd
            // 
            this.buttonAdd.Location = new System.Drawing.Point(235, 100);
            this.buttonAdd.Name = "buttonAdd";
            this.buttonAdd.Size = new System.Drawing.Size(152, 31);
            this.buttonAdd.TabIndex = 4;
            this.buttonAdd.Text = "Voir la fiche en cours";
            this.buttonAdd.UseVisualStyleBackColor = true;
            this.buttonAdd.Click += new System.EventHandler(this.buttonAdd_Click);
            // 
            // panelView
            // 
            this.panelView.Controls.Add(this.panelComboView);
            this.panelView.Controls.Add(this.buttonComboView);
            this.panelView.Controls.Add(this.comboBoxView);
            this.panelView.Controls.Add(this.labelView);
            this.panelView.Location = new System.Drawing.Point(18, 150);
            this.panelView.Name = "panelView";
            this.panelView.Size = new System.Drawing.Size(976, 549);
            this.panelView.TabIndex = 5;
            this.panelView.Visible = false;
            // 
            // panelComboView
            // 
            this.panelComboView.Controls.Add(this.textBoxComboDate);
            this.panelComboView.Controls.Add(this.dataGridViewComboNonForfaitise);
            this.panelComboView.Controls.Add(this.labelNonForfaitiseCombo);
            this.panelComboView.Controls.Add(this.labelForfaitiseCombo);
            this.panelComboView.Controls.Add(this.dataGridViewComboForfaitise);
            this.panelComboView.Controls.Add(this.labelComboDate);
            this.panelComboView.Controls.Add(this.textBoxCombo);
            this.panelComboView.Controls.Add(this.labelComboEtat);
            this.panelComboView.Location = new System.Drawing.Point(12, 96);
            this.panelComboView.Name = "panelComboView";
            this.panelComboView.Size = new System.Drawing.Size(940, 433);
            this.panelComboView.TabIndex = 3;
            this.panelComboView.Visible = false;
            // 
            // textBoxComboDate
            // 
            this.textBoxComboDate.Location = new System.Drawing.Point(75, 48);
            this.textBoxComboDate.Name = "textBoxComboDate";
            this.textBoxComboDate.ReadOnly = true;
            this.textBoxComboDate.Size = new System.Drawing.Size(100, 22);
            this.textBoxComboDate.TabIndex = 8;
            // 
            // dataGridViewComboNonForfaitise
            // 
            this.dataGridViewComboNonForfaitise.AllowUserToAddRows = false;
            this.dataGridViewComboNonForfaitise.AllowUserToDeleteRows = false;
            this.dataGridViewComboNonForfaitise.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewComboNonForfaitise.Location = new System.Drawing.Point(456, 118);
            this.dataGridViewComboNonForfaitise.Name = "dataGridViewComboNonForfaitise";
            this.dataGridViewComboNonForfaitise.ReadOnly = true;
            this.dataGridViewComboNonForfaitise.RowTemplate.Height = 24;
            this.dataGridViewComboNonForfaitise.Size = new System.Drawing.Size(447, 207);
            this.dataGridViewComboNonForfaitise.TabIndex = 7;
            // 
            // labelNonForfaitiseCombo
            // 
            this.labelNonForfaitiseCombo.AutoSize = true;
            this.labelNonForfaitiseCombo.Location = new System.Drawing.Point(453, 98);
            this.labelNonForfaitiseCombo.Name = "labelNonForfaitiseCombo";
            this.labelNonForfaitiseCombo.Size = new System.Drawing.Size(140, 17);
            this.labelNonForfaitiseCombo.TabIndex = 6;
            this.labelNonForfaitiseCombo.Text = "Frais non forfaitisés: ";
            // 
            // labelForfaitiseCombo
            // 
            this.labelForfaitiseCombo.AutoSize = true;
            this.labelForfaitiseCombo.Location = new System.Drawing.Point(20, 98);
            this.labelForfaitiseCombo.Name = "labelForfaitiseCombo";
            this.labelForfaitiseCombo.Size = new System.Drawing.Size(112, 17);
            this.labelForfaitiseCombo.TabIndex = 5;
            this.labelForfaitiseCombo.Text = "Frais Forfaitisés:";
            // 
            // dataGridViewComboForfaitise
            // 
            this.dataGridViewComboForfaitise.AllowUserToAddRows = false;
            this.dataGridViewComboForfaitise.AllowUserToDeleteRows = false;
            this.dataGridViewComboForfaitise.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewComboForfaitise.Location = new System.Drawing.Point(23, 118);
            this.dataGridViewComboForfaitise.Name = "dataGridViewComboForfaitise";
            this.dataGridViewComboForfaitise.ReadOnly = true;
            this.dataGridViewComboForfaitise.RowTemplate.Height = 24;
            this.dataGridViewComboForfaitise.Size = new System.Drawing.Size(334, 150);
            this.dataGridViewComboForfaitise.TabIndex = 4;
            // 
            // labelComboDate
            // 
            this.labelComboDate.AutoSize = true;
            this.labelComboDate.Location = new System.Drawing.Point(20, 51);
            this.labelComboDate.Name = "labelComboDate";
            this.labelComboDate.Size = new System.Drawing.Size(42, 17);
            this.labelComboDate.TabIndex = 2;
            this.labelComboDate.Text = "Date:";
            // 
            // textBoxCombo
            // 
            this.textBoxCombo.Location = new System.Drawing.Point(75, 13);
            this.textBoxCombo.Name = "textBoxCombo";
            this.textBoxCombo.ReadOnly = true;
            this.textBoxCombo.Size = new System.Drawing.Size(200, 22);
            this.textBoxCombo.TabIndex = 1;
            // 
            // labelComboEtat
            // 
            this.labelComboEtat.AutoSize = true;
            this.labelComboEtat.Location = new System.Drawing.Point(20, 16);
            this.labelComboEtat.Name = "labelComboEtat";
            this.labelComboEtat.Size = new System.Drawing.Size(37, 17);
            this.labelComboEtat.TabIndex = 0;
            this.labelComboEtat.Text = "État:";
            // 
            // buttonComboView
            // 
            this.buttonComboView.Location = new System.Drawing.Point(152, 52);
            this.buttonComboView.Name = "buttonComboView";
            this.buttonComboView.Size = new System.Drawing.Size(83, 23);
            this.buttonComboView.TabIndex = 2;
            this.buttonComboView.Text = "Visualiser";
            this.buttonComboView.UseVisualStyleBackColor = true;
            this.buttonComboView.Click += new System.EventHandler(this.buttonComboView_Click);
            // 
            // comboBoxView
            // 
            this.comboBoxView.FormattingEnabled = true;
            this.comboBoxView.Location = new System.Drawing.Point(10, 52);
            this.comboBoxView.Name = "comboBoxView";
            this.comboBoxView.Size = new System.Drawing.Size(121, 24);
            this.comboBoxView.TabIndex = 1;
            // 
            // labelView
            // 
            this.labelView.AutoSize = true;
            this.labelView.Location = new System.Drawing.Point(9, 13);
            this.labelView.Name = "labelView";
            this.labelView.Size = new System.Drawing.Size(197, 17);
            this.labelView.TabIndex = 0;
            this.labelView.Text = "Veuilez sélectionner une fiche";
            // 
            // panelAdd
            // 
            this.panelAdd.Controls.Add(this.labelAddFicheHorsFraisDataGridView);
            this.panelAdd.Controls.Add(this.labelAddFicheHorsFraisButton);
            this.panelAdd.Controls.Add(this.labelAddFicheHorsFraisDateTimePicker);
            this.panelAdd.Controls.Add(this.labelAddFicheHorsFraisNumericUpDown);
            this.panelAdd.Controls.Add(this.labelAddFicheHorsFraisTextBox);
            this.panelAdd.Controls.Add(this.labelAddFicheHorsFraisDate);
            this.panelAdd.Controls.Add(this.labelAddFicheHorsFraisMontant);
            this.panelAdd.Controls.Add(this.labelAddFicheHorsFraisLibelle);
            this.panelAdd.Controls.Add(this.labelAddFicheHorsFrais);
            this.panelAdd.Controls.Add(this.labelAddFicheFraisDataGridView);
            this.panelAdd.Controls.Add(this.FicheFraisAddValid);
            this.panelAdd.Controls.Add(this.FicheFraisNumericUpDown);
            this.panelAdd.Controls.Add(this.labelAddFicheFraisQuantity);
            this.panelAdd.Controls.Add(this.labelAddFicheFraisComboBox);
            this.panelAdd.Controls.Add(this.labelAddFicheFraisChoice);
            this.panelAdd.Controls.Add(this.labelAddFicheFrais);
            this.panelAdd.Location = new System.Drawing.Point(18, 150);
            this.panelAdd.Name = "panelAdd";
            this.panelAdd.Size = new System.Drawing.Size(976, 549);
            this.panelAdd.TabIndex = 6;
            this.panelAdd.Visible = false;
            // 
            // labelAddFicheHorsFraisDataGridView
            // 
            this.labelAddFicheHorsFraisDataGridView.AllowUserToAddRows = false;
            this.labelAddFicheHorsFraisDataGridView.AllowUserToDeleteRows = false;
            this.labelAddFicheHorsFraisDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.labelAddFicheHorsFraisDataGridView.Location = new System.Drawing.Point(585, 284);
            this.labelAddFicheHorsFraisDataGridView.Name = "labelAddFicheHorsFraisDataGridView";
            this.labelAddFicheHorsFraisDataGridView.ReadOnly = true;
            this.labelAddFicheHorsFraisDataGridView.RowTemplate.Height = 24;
            this.labelAddFicheHorsFraisDataGridView.Size = new System.Drawing.Size(367, 206);
            this.labelAddFicheHorsFraisDataGridView.TabIndex = 16;
            // 
            // labelAddFicheHorsFraisButton
            // 
            this.labelAddFicheHorsFraisButton.Location = new System.Drawing.Point(387, 467);
            this.labelAddFicheHorsFraisButton.Name = "labelAddFicheHorsFraisButton";
            this.labelAddFicheHorsFraisButton.Size = new System.Drawing.Size(75, 23);
            this.labelAddFicheHorsFraisButton.TabIndex = 15;
            this.labelAddFicheHorsFraisButton.Text = "Valider";
            this.labelAddFicheHorsFraisButton.UseVisualStyleBackColor = true;
            this.labelAddFicheHorsFraisButton.Click += new System.EventHandler(this.labelAddFicheHorsFraisButton_Click);
            // 
            // labelAddFicheHorsFraisDateTimePicker
            // 
            this.labelAddFicheHorsFraisDateTimePicker.Location = new System.Drawing.Point(262, 419);
            this.labelAddFicheHorsFraisDateTimePicker.Name = "labelAddFicheHorsFraisDateTimePicker";
            this.labelAddFicheHorsFraisDateTimePicker.Size = new System.Drawing.Size(200, 22);
            this.labelAddFicheHorsFraisDateTimePicker.TabIndex = 14;
            // 
            // labelAddFicheHorsFraisNumericUpDown
            // 
            this.labelAddFicheHorsFraisNumericUpDown.Location = new System.Drawing.Point(329, 376);
            this.labelAddFicheHorsFraisNumericUpDown.Name = "labelAddFicheHorsFraisNumericUpDown";
            this.labelAddFicheHorsFraisNumericUpDown.Size = new System.Drawing.Size(133, 22);
            this.labelAddFicheHorsFraisNumericUpDown.TabIndex = 13;
            // 
            // labelAddFicheHorsFraisTextBox
            // 
            this.labelAddFicheHorsFraisTextBox.Location = new System.Drawing.Point(262, 333);
            this.labelAddFicheHorsFraisTextBox.Name = "labelAddFicheHorsFraisTextBox";
            this.labelAddFicheHorsFraisTextBox.Size = new System.Drawing.Size(200, 22);
            this.labelAddFicheHorsFraisTextBox.TabIndex = 12;
            // 
            // labelAddFicheHorsFraisDate
            // 
            this.labelAddFicheHorsFraisDate.AutoSize = true;
            this.labelAddFicheHorsFraisDate.Location = new System.Drawing.Point(65, 419);
            this.labelAddFicheHorsFraisDate.Name = "labelAddFicheHorsFraisDate";
            this.labelAddFicheHorsFraisDate.Size = new System.Drawing.Size(147, 17);
            this.labelAddFicheHorsFraisDate.TabIndex = 11;
            this.labelAddFicheHorsFraisDate.Text = "Séléctionner une date";
            // 
            // labelAddFicheHorsFraisMontant
            // 
            this.labelAddFicheHorsFraisMontant.AutoSize = true;
            this.labelAddFicheHorsFraisMontant.Location = new System.Drawing.Point(65, 378);
            this.labelAddFicheHorsFraisMontant.Name = "labelAddFicheHorsFraisMontant";
            this.labelAddFicheHorsFraisMontant.Size = new System.Drawing.Size(162, 17);
            this.labelAddFicheHorsFraisMontant.TabIndex = 10;
            this.labelAddFicheHorsFraisMontant.Text = "Sélectionner un montant";
            // 
            // labelAddFicheHorsFraisLibelle
            // 
            this.labelAddFicheHorsFraisLibelle.AutoSize = true;
            this.labelAddFicheHorsFraisLibelle.Location = new System.Drawing.Point(65, 336);
            this.labelAddFicheHorsFraisLibelle.Name = "labelAddFicheHorsFraisLibelle";
            this.labelAddFicheHorsFraisLibelle.Size = new System.Drawing.Size(147, 17);
            this.labelAddFicheHorsFraisLibelle.TabIndex = 9;
            this.labelAddFicheHorsFraisLibelle.Text = "Séléctionner un libellé";
            // 
            // labelAddFicheHorsFrais
            // 
            this.labelAddFicheHorsFrais.AutoSize = true;
            this.labelAddFicheHorsFrais.Font = new System.Drawing.Font("Microsoft Sans Serif", 7.8F, System.Drawing.FontStyle.Underline, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelAddFicheHorsFrais.Location = new System.Drawing.Point(6, 284);
            this.labelAddFicheHorsFrais.Name = "labelAddFicheHorsFrais";
            this.labelAddFicheHorsFrais.Size = new System.Drawing.Size(125, 17);
            this.labelAddFicheHorsFrais.TabIndex = 8;
            this.labelAddFicheHorsFrais.Text = "Frais non forfaitisé";
            // 
            // labelAddFicheFraisDataGridView
            // 
            this.labelAddFicheFraisDataGridView.AllowUserToAddRows = false;
            this.labelAddFicheFraisDataGridView.AllowUserToDeleteRows = false;
            this.labelAddFicheFraisDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.labelAddFicheFraisDataGridView.Location = new System.Drawing.Point(585, 59);
            this.labelAddFicheFraisDataGridView.Name = "labelAddFicheFraisDataGridView";
            this.labelAddFicheFraisDataGridView.ReadOnly = true;
            this.labelAddFicheFraisDataGridView.RowTemplate.Height = 24;
            this.labelAddFicheFraisDataGridView.Size = new System.Drawing.Size(367, 199);
            this.labelAddFicheFraisDataGridView.TabIndex = 7;
            // 
            // FicheFraisAddValid
            // 
            this.FicheFraisAddValid.Location = new System.Drawing.Point(387, 191);
            this.FicheFraisAddValid.Name = "FicheFraisAddValid";
            this.FicheFraisAddValid.Size = new System.Drawing.Size(75, 23);
            this.FicheFraisAddValid.TabIndex = 6;
            this.FicheFraisAddValid.Text = "Valider";
            this.FicheFraisAddValid.UseVisualStyleBackColor = true;
            this.FicheFraisAddValid.Click += new System.EventHandler(this.FicheFraisAddValid_Click);
            // 
            // FicheFraisNumericUpDown
            // 
            this.FicheFraisNumericUpDown.Location = new System.Drawing.Point(329, 147);
            this.FicheFraisNumericUpDown.Maximum = new decimal(new int[] {
            100000,
            0,
            0,
            0});
            this.FicheFraisNumericUpDown.Name = "FicheFraisNumericUpDown";
            this.FicheFraisNumericUpDown.Size = new System.Drawing.Size(133, 22);
            this.FicheFraisNumericUpDown.TabIndex = 5;
            // 
            // labelAddFicheFraisQuantity
            // 
            this.labelAddFicheFraisQuantity.AutoSize = true;
            this.labelAddFicheFraisQuantity.Location = new System.Drawing.Point(65, 149);
            this.labelAddFicheFraisQuantity.Name = "labelAddFicheFraisQuantity";
            this.labelAddFicheFraisQuantity.Size = new System.Drawing.Size(170, 17);
            this.labelAddFicheFraisQuantity.TabIndex = 4;
            this.labelAddFicheFraisQuantity.Text = "Séléctionner une quantité";
            // 
            // labelAddFicheFraisComboBox
            // 
            this.labelAddFicheFraisComboBox.Location = new System.Drawing.Point(262, 106);
            this.labelAddFicheFraisComboBox.Name = "labelAddFicheFraisComboBox";
            this.labelAddFicheFraisComboBox.Size = new System.Drawing.Size(200, 24);
            this.labelAddFicheFraisComboBox.TabIndex = 3;
            // 
            // labelAddFicheFraisChoice
            // 
            this.labelAddFicheFraisChoice.AutoSize = true;
            this.labelAddFicheFraisChoice.Location = new System.Drawing.Point(65, 109);
            this.labelAddFicheFraisChoice.Name = "labelAddFicheFraisChoice";
            this.labelAddFicheFraisChoice.Size = new System.Drawing.Size(138, 17);
            this.labelAddFicheFraisChoice.TabIndex = 2;
            this.labelAddFicheFraisChoice.Text = "Séléctionner un frais";
            // 
            // labelAddFicheFrais
            // 
            this.labelAddFicheFrais.AutoSize = true;
            this.labelAddFicheFrais.Font = new System.Drawing.Font("Microsoft Sans Serif", 7.8F, System.Drawing.FontStyle.Underline, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelAddFicheFrais.Location = new System.Drawing.Point(6, 59);
            this.labelAddFicheFrais.Name = "labelAddFicheFrais";
            this.labelAddFicheFrais.Size = new System.Drawing.Size(97, 17);
            this.labelAddFicheFrais.TabIndex = 1;
            this.labelAddFicheFrais.Text = "Frais forfaitisé";
            // 
            // Gestion
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1006, 721);
            this.Controls.Add(this.buttonAdd);
            this.Controls.Add(this.buttonView);
            this.Controls.Add(this.labelQuestion);
            this.Controls.Add(this.labelLogout);
            this.Controls.Add(this.buttonLogout);
            this.Controls.Add(this.panelAdd);
            this.Controls.Add(this.panelView);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "Gestion";
            this.Text = "GSB - Gestion";
            this.Load += new System.EventHandler(this.Gestion_Load);
            this.panelView.ResumeLayout(false);
            this.panelView.PerformLayout();
            this.panelComboView.ResumeLayout(false);
            this.panelComboView.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewComboNonForfaitise)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewComboForfaitise)).EndInit();
            this.panelAdd.ResumeLayout(false);
            this.panelAdd.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.labelAddFicheHorsFraisDataGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.labelAddFicheHorsFraisNumericUpDown)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.labelAddFicheFraisDataGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.FicheFraisNumericUpDown)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button buttonLogout;
        private System.Windows.Forms.Label labelLogout;
        private System.Windows.Forms.Label labelQuestion;
        private System.Windows.Forms.Button buttonView;
        private System.Windows.Forms.Button buttonAdd;
        private System.Windows.Forms.Panel panelView;
        private System.Windows.Forms.Panel panelAdd;
        private System.Windows.Forms.ComboBox labelAddFicheFraisComboBox;
        private System.Windows.Forms.Label labelAddFicheFraisChoice;
        private System.Windows.Forms.Label labelAddFicheFrais;
        private System.Windows.Forms.Button FicheFraisAddValid;
        private System.Windows.Forms.NumericUpDown FicheFraisNumericUpDown;
        private System.Windows.Forms.Label labelAddFicheFraisQuantity;
        private System.Windows.Forms.DataGridView labelAddFicheFraisDataGridView;
        private System.Windows.Forms.Label labelAddFicheHorsFrais;
        private System.Windows.Forms.Label labelAddFicheHorsFraisLibelle;
        private System.Windows.Forms.DateTimePicker labelAddFicheHorsFraisDateTimePicker;
        private System.Windows.Forms.NumericUpDown labelAddFicheHorsFraisNumericUpDown;
        private System.Windows.Forms.TextBox labelAddFicheHorsFraisTextBox;
        private System.Windows.Forms.Label labelAddFicheHorsFraisDate;
        private System.Windows.Forms.Label labelAddFicheHorsFraisMontant;
        private System.Windows.Forms.DataGridView labelAddFicheHorsFraisDataGridView;
        private System.Windows.Forms.Button labelAddFicheHorsFraisButton;
        private System.Windows.Forms.Label labelView;
        private System.Windows.Forms.ComboBox comboBoxView;
        private System.Windows.Forms.Button buttonComboView;
        private System.Windows.Forms.Panel panelComboView;
        private System.Windows.Forms.DataGridView dataGridViewComboNonForfaitise;
        private System.Windows.Forms.Label labelNonForfaitiseCombo;
        private System.Windows.Forms.Label labelForfaitiseCombo;
        private System.Windows.Forms.DataGridView dataGridViewComboForfaitise;
        private System.Windows.Forms.Label labelComboDate;
        private System.Windows.Forms.TextBox textBoxCombo;
        private System.Windows.Forms.Label labelComboEtat;
        private System.Windows.Forms.TextBox textBoxComboDate;

    }
}