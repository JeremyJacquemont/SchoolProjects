namespace GSB
{
    partial class Comptable
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Comptable));
            this.buttonSuivi = new System.Windows.Forms.Button();
            this.buttonView = new System.Windows.Forms.Button();
            this.labelQuestion = new System.Windows.Forms.Label();
            this.labelLogout = new System.Windows.Forms.Label();
            this.buttonLogout = new System.Windows.Forms.Button();
            this.panelView = new System.Windows.Forms.Panel();
            this.textBoxDate = new System.Windows.Forms.TextBox();
            this.labelDate = new System.Windows.Forms.Label();
            this.textBoxEtat = new System.Windows.Forms.TextBox();
            this.labelEtat = new System.Windows.Forms.Label();
            this.buttonValidateComptableView = new System.Windows.Forms.Button();
            this.buttonResetComptableView = new System.Windows.Forms.Button();
            this.labelValidateComptableForfaitise = new System.Windows.Forms.Label();
            this.buttonModifyForfait = new System.Windows.Forms.Button();
            this.numericUpDownModifyForfait = new System.Windows.Forms.NumericUpDown();
            this.labelModifyForfait = new System.Windows.Forms.Label();
            this.dataGridViewComptableNonForfaitise = new System.Windows.Forms.DataGridView();
            this.labelNonForfaitiseViewComptable = new System.Windows.Forms.Label();
            this.labelForfaitiseViewComptable = new System.Windows.Forms.Label();
            this.buttonValidateSelect = new System.Windows.Forms.Button();
            this.dateTimePickerSelectDate = new System.Windows.Forms.DateTimePicker();
            this.labelSelectDate = new System.Windows.Forms.Label();
            this.comboBoxSelectUser = new System.Windows.Forms.ComboBox();
            this.labelSelectUser = new System.Windows.Forms.Label();
            this.dataGridViewComptableForfaitise = new System.Windows.Forms.DataGridView();
            this.panelSuivi = new System.Windows.Forms.Panel();
            this.buttonSuiviRembourse = new System.Windows.Forms.Button();
            this.buttonSuiviPaiement = new System.Windows.Forms.Button();
            this.textBoxDateSuivi = new System.Windows.Forms.TextBox();
            this.labelComboDateSuivi = new System.Windows.Forms.Label();
            this.labelComboEtatSuivi = new System.Windows.Forms.Label();
            this.dataGridViewNonFraisSuivi = new System.Windows.Forms.DataGridView();
            this.labelNonFraisSuivi = new System.Windows.Forms.Label();
            this.labelFraisSuivi = new System.Windows.Forms.Label();
            this.dataGridViewFraisSuivi = new System.Windows.Forms.DataGridView();
            this.buttonValiderSuivi = new System.Windows.Forms.Button();
            this.dateTimePickerSelectDateSuivi = new System.Windows.Forms.DateTimePicker();
            this.labelSelectDateSuivi = new System.Windows.Forms.Label();
            this.comboBoxSelectUserSuivi = new System.Windows.Forms.ComboBox();
            this.labelSelectUserSuivi = new System.Windows.Forms.Label();
            this.textBoxEtatSuivi = new System.Windows.Forms.TextBox();
            this.panelView.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownModifyForfait)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewComptableNonForfaitise)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewComptableForfaitise)).BeginInit();
            this.panelSuivi.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewNonFraisSuivi)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewFraisSuivi)).BeginInit();
            this.SuspendLayout();
            // 
            // buttonSuivi
            // 
            this.buttonSuivi.Location = new System.Drawing.Point(208, 100);
            this.buttonSuivi.Name = "buttonSuivi";
            this.buttonSuivi.Size = new System.Drawing.Size(152, 31);
            this.buttonSuivi.TabIndex = 9;
            this.buttonSuivi.Text = "Suive le paiement";
            this.buttonSuivi.UseVisualStyleBackColor = true;
            this.buttonSuivi.Click += new System.EventHandler(this.buttonSuivi_Click);
            // 
            // buttonView
            // 
            this.buttonView.Location = new System.Drawing.Point(21, 100);
            this.buttonView.Name = "buttonView";
            this.buttonView.Size = new System.Drawing.Size(181, 31);
            this.buttonView.TabIndex = 8;
            this.buttonView.Text = "Valider les fiches de frais";
            this.buttonView.UseVisualStyleBackColor = true;
            this.buttonView.Click += new System.EventHandler(this.buttonView_Click);
            // 
            // labelQuestion
            // 
            this.labelQuestion.AutoSize = true;
            this.labelQuestion.Location = new System.Drawing.Point(18, 71);
            this.labelQuestion.Name = "labelQuestion";
            this.labelQuestion.Size = new System.Drawing.Size(155, 17);
            this.labelQuestion.TabIndex = 7;
            this.labelQuestion.Text = "Que voulez-vous faire?";
            // 
            // labelLogout
            // 
            this.labelLogout.AutoSize = true;
            this.labelLogout.Location = new System.Drawing.Point(15, 18);
            this.labelLogout.Name = "labelLogout";
            this.labelLogout.Size = new System.Drawing.Size(0, 17);
            this.labelLogout.TabIndex = 6;
            // 
            // buttonLogout
            // 
            this.buttonLogout.Location = new System.Drawing.Point(860, 12);
            this.buttonLogout.Name = "buttonLogout";
            this.buttonLogout.Size = new System.Drawing.Size(137, 23);
            this.buttonLogout.TabIndex = 5;
            this.buttonLogout.Text = "DECONNECTION";
            this.buttonLogout.UseVisualStyleBackColor = true;
            this.buttonLogout.Click += new System.EventHandler(this.buttonLogout_Click);
            // 
            // panelView
            // 
            this.panelView.Controls.Add(this.textBoxDate);
            this.panelView.Controls.Add(this.labelDate);
            this.panelView.Controls.Add(this.textBoxEtat);
            this.panelView.Controls.Add(this.labelEtat);
            this.panelView.Controls.Add(this.buttonValidateComptableView);
            this.panelView.Controls.Add(this.buttonResetComptableView);
            this.panelView.Controls.Add(this.labelValidateComptableForfaitise);
            this.panelView.Controls.Add(this.buttonModifyForfait);
            this.panelView.Controls.Add(this.numericUpDownModifyForfait);
            this.panelView.Controls.Add(this.labelModifyForfait);
            this.panelView.Controls.Add(this.dataGridViewComptableNonForfaitise);
            this.panelView.Controls.Add(this.labelNonForfaitiseViewComptable);
            this.panelView.Controls.Add(this.labelForfaitiseViewComptable);
            this.panelView.Controls.Add(this.buttonValidateSelect);
            this.panelView.Controls.Add(this.dateTimePickerSelectDate);
            this.panelView.Controls.Add(this.labelSelectDate);
            this.panelView.Controls.Add(this.comboBoxSelectUser);
            this.panelView.Controls.Add(this.labelSelectUser);
            this.panelView.Controls.Add(this.dataGridViewComptableForfaitise);
            this.panelView.Location = new System.Drawing.Point(12, 148);
            this.panelView.Name = "panelView";
            this.panelView.Size = new System.Drawing.Size(982, 561);
            this.panelView.TabIndex = 10;
            this.panelView.Visible = false;
            // 
            // textBoxDate
            // 
            this.textBoxDate.Location = new System.Drawing.Point(71, 185);
            this.textBoxDate.Name = "textBoxDate";
            this.textBoxDate.ReadOnly = true;
            this.textBoxDate.Size = new System.Drawing.Size(100, 22);
            this.textBoxDate.TabIndex = 23;
            this.textBoxDate.Visible = false;
            // 
            // labelDate
            // 
            this.labelDate.AutoSize = true;
            this.labelDate.Location = new System.Drawing.Point(16, 188);
            this.labelDate.Name = "labelDate";
            this.labelDate.Size = new System.Drawing.Size(42, 17);
            this.labelDate.TabIndex = 22;
            this.labelDate.Text = "Date:";
            this.labelDate.Visible = false;
            // 
            // textBoxEtat
            // 
            this.textBoxEtat.Location = new System.Drawing.Point(71, 150);
            this.textBoxEtat.Name = "textBoxEtat";
            this.textBoxEtat.ReadOnly = true;
            this.textBoxEtat.Size = new System.Drawing.Size(200, 22);
            this.textBoxEtat.TabIndex = 21;
            this.textBoxEtat.Visible = false;
            // 
            // labelEtat
            // 
            this.labelEtat.AutoSize = true;
            this.labelEtat.Location = new System.Drawing.Point(16, 153);
            this.labelEtat.Name = "labelEtat";
            this.labelEtat.Size = new System.Drawing.Size(37, 17);
            this.labelEtat.TabIndex = 20;
            this.labelEtat.Text = "État:";
            this.labelEtat.Visible = false;
            // 
            // buttonValidateComptableView
            // 
            this.buttonValidateComptableView.Location = new System.Drawing.Point(848, 468);
            this.buttonValidateComptableView.Name = "buttonValidateComptableView";
            this.buttonValidateComptableView.Size = new System.Drawing.Size(75, 23);
            this.buttonValidateComptableView.TabIndex = 17;
            this.buttonValidateComptableView.Text = "Valider";
            this.buttonValidateComptableView.UseVisualStyleBackColor = true;
            this.buttonValidateComptableView.Visible = false;
            this.buttonValidateComptableView.Click += new System.EventHandler(this.buttonValidateComptableView_Click);
            // 
            // buttonResetComptableView
            // 
            this.buttonResetComptableView.Location = new System.Drawing.Point(750, 468);
            this.buttonResetComptableView.Name = "buttonResetComptableView";
            this.buttonResetComptableView.Size = new System.Drawing.Size(75, 23);
            this.buttonResetComptableView.TabIndex = 16;
            this.buttonResetComptableView.Text = "Reset";
            this.buttonResetComptableView.UseVisualStyleBackColor = true;
            this.buttonResetComptableView.Visible = false;
            this.buttonResetComptableView.Click += new System.EventHandler(this.buttonResetComptableView_Click);
            // 
            // labelValidateComptableForfaitise
            // 
            this.labelValidateComptableForfaitise.AutoSize = true;
            this.labelValidateComptableForfaitise.ForeColor = System.Drawing.Color.Green;
            this.labelValidateComptableForfaitise.Location = new System.Drawing.Point(131, 399);
            this.labelValidateComptableForfaitise.Name = "labelValidateComptableForfaitise";
            this.labelValidateComptableForfaitise.Size = new System.Drawing.Size(246, 17);
            this.labelValidateComptableForfaitise.TabIndex = 15;
            this.labelValidateComptableForfaitise.Text = "Les changements ont été enregistrés!";
            this.labelValidateComptableForfaitise.Visible = false;
            // 
            // buttonModifyForfait
            // 
            this.buttonModifyForfait.Location = new System.Drawing.Point(43, 509);
            this.buttonModifyForfait.Name = "buttonModifyForfait";
            this.buttonModifyForfait.Size = new System.Drawing.Size(75, 23);
            this.buttonModifyForfait.TabIndex = 14;
            this.buttonModifyForfait.Text = "Valider";
            this.buttonModifyForfait.UseVisualStyleBackColor = true;
            this.buttonModifyForfait.Visible = false;
            this.buttonModifyForfait.Click += new System.EventHandler(this.buttonModifyForfait_Click);
            // 
            // numericUpDownModifyForfait
            // 
            this.numericUpDownModifyForfait.Location = new System.Drawing.Point(43, 469);
            this.numericUpDownModifyForfait.Name = "numericUpDownModifyForfait";
            this.numericUpDownModifyForfait.Size = new System.Drawing.Size(92, 22);
            this.numericUpDownModifyForfait.TabIndex = 13;
            this.numericUpDownModifyForfait.Visible = false;
            // 
            // labelModifyForfait
            // 
            this.labelModifyForfait.AutoSize = true;
            this.labelModifyForfait.Location = new System.Drawing.Point(40, 436);
            this.labelModifyForfait.Name = "labelModifyForfait";
            this.labelModifyForfait.Size = new System.Drawing.Size(193, 17);
            this.labelModifyForfait.TabIndex = 12;
            this.labelModifyForfait.Text = "Modifier la valeur de {name} :";
            this.labelModifyForfait.Visible = false;
            // 
            // dataGridViewComptableNonForfaitise
            // 
            this.dataGridViewComptableNonForfaitise.AllowUserToAddRows = false;
            this.dataGridViewComptableNonForfaitise.AllowUserToDeleteRows = false;
            this.dataGridViewComptableNonForfaitise.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewComptableNonForfaitise.Location = new System.Drawing.Point(476, 246);
            this.dataGridViewComptableNonForfaitise.Name = "dataGridViewComptableNonForfaitise";
            this.dataGridViewComptableNonForfaitise.ReadOnly = true;
            this.dataGridViewComptableNonForfaitise.RowTemplate.Height = 24;
            this.dataGridViewComptableNonForfaitise.Size = new System.Drawing.Size(447, 207);
            this.dataGridViewComptableNonForfaitise.TabIndex = 11;
            this.dataGridViewComptableNonForfaitise.Visible = false;
            this.dataGridViewComptableNonForfaitise.CellMouseDown += new System.Windows.Forms.DataGridViewCellMouseEventHandler(this.dataGridViewComptableNonForfaitise_CellMouseDown);
            // 
            // labelNonForfaitiseViewComptable
            // 
            this.labelNonForfaitiseViewComptable.AutoSize = true;
            this.labelNonForfaitiseViewComptable.Location = new System.Drawing.Point(473, 226);
            this.labelNonForfaitiseViewComptable.Name = "labelNonForfaitiseViewComptable";
            this.labelNonForfaitiseViewComptable.Size = new System.Drawing.Size(140, 17);
            this.labelNonForfaitiseViewComptable.TabIndex = 10;
            this.labelNonForfaitiseViewComptable.Text = "Frais non forfaitisés: ";
            this.labelNonForfaitiseViewComptable.Visible = false;
            // 
            // labelForfaitiseViewComptable
            // 
            this.labelForfaitiseViewComptable.AutoSize = true;
            this.labelForfaitiseViewComptable.Location = new System.Drawing.Point(40, 226);
            this.labelForfaitiseViewComptable.Name = "labelForfaitiseViewComptable";
            this.labelForfaitiseViewComptable.Size = new System.Drawing.Size(112, 17);
            this.labelForfaitiseViewComptable.TabIndex = 9;
            this.labelForfaitiseViewComptable.Text = "Frais Forfaitisés:";
            this.labelForfaitiseViewComptable.Visible = false;
            // 
            // buttonValidateSelect
            // 
            this.buttonValidateSelect.Location = new System.Drawing.Point(12, 106);
            this.buttonValidateSelect.Name = "buttonValidateSelect";
            this.buttonValidateSelect.Size = new System.Drawing.Size(75, 23);
            this.buttonValidateSelect.TabIndex = 4;
            this.buttonValidateSelect.Text = "Valider";
            this.buttonValidateSelect.UseVisualStyleBackColor = true;
            this.buttonValidateSelect.Click += new System.EventHandler(this.buttonValidateSelect_Click);
            // 
            // dateTimePickerSelectDate
            // 
            this.dateTimePickerSelectDate.CustomFormat = "";
            this.dateTimePickerSelectDate.Location = new System.Drawing.Point(196, 60);
            this.dateTimePickerSelectDate.Name = "dateTimePickerSelectDate";
            this.dateTimePickerSelectDate.Size = new System.Drawing.Size(200, 22);
            this.dateTimePickerSelectDate.TabIndex = 3;
            // 
            // labelSelectDate
            // 
            this.labelSelectDate.AutoSize = true;
            this.labelSelectDate.Location = new System.Drawing.Point(9, 65);
            this.labelSelectDate.Name = "labelSelectDate";
            this.labelSelectDate.Size = new System.Drawing.Size(151, 17);
            this.labelSelectDate.TabIndex = 2;
            this.labelSelectDate.Text = "Sélectionner une date:";
            // 
            // comboBoxSelectUser
            // 
            this.comboBoxSelectUser.FormattingEnabled = true;
            this.comboBoxSelectUser.Location = new System.Drawing.Point(196, 18);
            this.comboBoxSelectUser.Name = "comboBoxSelectUser";
            this.comboBoxSelectUser.Size = new System.Drawing.Size(152, 24);
            this.comboBoxSelectUser.TabIndex = 1;
            // 
            // labelSelectUser
            // 
            this.labelSelectUser.AutoSize = true;
            this.labelSelectUser.Location = new System.Drawing.Point(9, 21);
            this.labelSelectUser.Name = "labelSelectUser";
            this.labelSelectUser.Size = new System.Drawing.Size(176, 17);
            this.labelSelectUser.TabIndex = 0;
            this.labelSelectUser.Text = "Sélectionner un utilisateur:";
            // 
            // dataGridViewComptableForfaitise
            // 
            this.dataGridViewComptableForfaitise.AllowUserToAddRows = false;
            this.dataGridViewComptableForfaitise.AllowUserToDeleteRows = false;
            this.dataGridViewComptableForfaitise.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewComptableForfaitise.Location = new System.Drawing.Point(43, 246);
            this.dataGridViewComptableForfaitise.Name = "dataGridViewComptableForfaitise";
            this.dataGridViewComptableForfaitise.RowTemplate.Height = 24;
            this.dataGridViewComptableForfaitise.Size = new System.Drawing.Size(334, 150);
            this.dataGridViewComptableForfaitise.TabIndex = 8;
            this.dataGridViewComptableForfaitise.Visible = false;
            this.dataGridViewComptableForfaitise.CellMouseDown += new System.Windows.Forms.DataGridViewCellMouseEventHandler(this.dataGridViewComptableForfaitise_CellMouseDown);
            // 
            // panelSuivi
            // 
            this.panelSuivi.Controls.Add(this.buttonSuiviRembourse);
            this.panelSuivi.Controls.Add(this.buttonSuiviPaiement);
            this.panelSuivi.Controls.Add(this.textBoxDateSuivi);
            this.panelSuivi.Controls.Add(this.labelComboDateSuivi);
            this.panelSuivi.Controls.Add(this.labelComboEtatSuivi);
            this.panelSuivi.Controls.Add(this.dataGridViewNonFraisSuivi);
            this.panelSuivi.Controls.Add(this.labelNonFraisSuivi);
            this.panelSuivi.Controls.Add(this.labelFraisSuivi);
            this.panelSuivi.Controls.Add(this.dataGridViewFraisSuivi);
            this.panelSuivi.Controls.Add(this.buttonValiderSuivi);
            this.panelSuivi.Controls.Add(this.dateTimePickerSelectDateSuivi);
            this.panelSuivi.Controls.Add(this.labelSelectDateSuivi);
            this.panelSuivi.Controls.Add(this.comboBoxSelectUserSuivi);
            this.panelSuivi.Controls.Add(this.labelSelectUserSuivi);
            this.panelSuivi.Controls.Add(this.textBoxEtatSuivi);
            this.panelSuivi.Location = new System.Drawing.Point(12, 148);
            this.panelSuivi.Name = "panelSuivi";
            this.panelSuivi.Size = new System.Drawing.Size(982, 561);
            this.panelSuivi.TabIndex = 11;
            this.panelSuivi.Visible = false;
            // 
            // buttonSuiviRembourse
            // 
            this.buttonSuiviRembourse.Location = new System.Drawing.Point(782, 497);
            this.buttonSuiviRembourse.Name = "buttonSuiviRembourse";
            this.buttonSuiviRembourse.Size = new System.Drawing.Size(141, 23);
            this.buttonSuiviRembourse.TabIndex = 21;
            this.buttonSuiviRembourse.Text = "Fiche remboursée";
            this.buttonSuiviRembourse.UseVisualStyleBackColor = true;
            this.buttonSuiviRembourse.Visible = false;
            this.buttonSuiviRembourse.Click += new System.EventHandler(this.buttonSuiviRembourse_Click);
            // 
            // buttonSuiviPaiement
            // 
            this.buttonSuiviPaiement.Location = new System.Drawing.Point(612, 497);
            this.buttonSuiviPaiement.Name = "buttonSuiviPaiement";
            this.buttonSuiviPaiement.Size = new System.Drawing.Size(153, 23);
            this.buttonSuiviPaiement.TabIndex = 20;
            this.buttonSuiviPaiement.Text = "Mettre en paiement";
            this.buttonSuiviPaiement.UseVisualStyleBackColor = true;
            this.buttonSuiviPaiement.Visible = false;
            this.buttonSuiviPaiement.Click += new System.EventHandler(this.buttonSuiviPaiement_Click);
            // 
            // textBoxDateSuivi
            // 
            this.textBoxDateSuivi.Location = new System.Drawing.Point(71, 185);
            this.textBoxDateSuivi.Name = "textBoxDateSuivi";
            this.textBoxDateSuivi.ReadOnly = true;
            this.textBoxDateSuivi.Size = new System.Drawing.Size(100, 22);
            this.textBoxDateSuivi.TabIndex = 19;
            this.textBoxDateSuivi.Visible = false;
            // 
            // labelComboDateSuivi
            // 
            this.labelComboDateSuivi.AutoSize = true;
            this.labelComboDateSuivi.Location = new System.Drawing.Point(16, 188);
            this.labelComboDateSuivi.Name = "labelComboDateSuivi";
            this.labelComboDateSuivi.Size = new System.Drawing.Size(42, 17);
            this.labelComboDateSuivi.TabIndex = 18;
            this.labelComboDateSuivi.Text = "Date:";
            this.labelComboDateSuivi.Visible = false;
            // 
            // labelComboEtatSuivi
            // 
            this.labelComboEtatSuivi.AutoSize = true;
            this.labelComboEtatSuivi.Location = new System.Drawing.Point(16, 153);
            this.labelComboEtatSuivi.Name = "labelComboEtatSuivi";
            this.labelComboEtatSuivi.Size = new System.Drawing.Size(37, 17);
            this.labelComboEtatSuivi.TabIndex = 16;
            this.labelComboEtatSuivi.Text = "État:";
            this.labelComboEtatSuivi.Visible = false;
            // 
            // dataGridViewNonFraisSuivi
            // 
            this.dataGridViewNonFraisSuivi.AllowUserToAddRows = false;
            this.dataGridViewNonFraisSuivi.AllowUserToDeleteRows = false;
            this.dataGridViewNonFraisSuivi.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewNonFraisSuivi.Location = new System.Drawing.Point(476, 246);
            this.dataGridViewNonFraisSuivi.Name = "dataGridViewNonFraisSuivi";
            this.dataGridViewNonFraisSuivi.ReadOnly = true;
            this.dataGridViewNonFraisSuivi.RowTemplate.Height = 24;
            this.dataGridViewNonFraisSuivi.Size = new System.Drawing.Size(447, 207);
            this.dataGridViewNonFraisSuivi.TabIndex = 15;
            this.dataGridViewNonFraisSuivi.Visible = false;
            // 
            // labelNonFraisSuivi
            // 
            this.labelNonFraisSuivi.AutoSize = true;
            this.labelNonFraisSuivi.Location = new System.Drawing.Point(473, 226);
            this.labelNonFraisSuivi.Name = "labelNonFraisSuivi";
            this.labelNonFraisSuivi.Size = new System.Drawing.Size(140, 17);
            this.labelNonFraisSuivi.TabIndex = 14;
            this.labelNonFraisSuivi.Text = "Frais non forfaitisés: ";
            this.labelNonFraisSuivi.Visible = false;
            // 
            // labelFraisSuivi
            // 
            this.labelFraisSuivi.AutoSize = true;
            this.labelFraisSuivi.Location = new System.Drawing.Point(40, 226);
            this.labelFraisSuivi.Name = "labelFraisSuivi";
            this.labelFraisSuivi.Size = new System.Drawing.Size(112, 17);
            this.labelFraisSuivi.TabIndex = 13;
            this.labelFraisSuivi.Text = "Frais Forfaitisés:";
            this.labelFraisSuivi.Visible = false;
            // 
            // dataGridViewFraisSuivi
            // 
            this.dataGridViewFraisSuivi.AllowUserToAddRows = false;
            this.dataGridViewFraisSuivi.AllowUserToDeleteRows = false;
            this.dataGridViewFraisSuivi.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewFraisSuivi.Location = new System.Drawing.Point(43, 246);
            this.dataGridViewFraisSuivi.Name = "dataGridViewFraisSuivi";
            this.dataGridViewFraisSuivi.RowTemplate.Height = 24;
            this.dataGridViewFraisSuivi.Size = new System.Drawing.Size(334, 150);
            this.dataGridViewFraisSuivi.TabIndex = 12;
            this.dataGridViewFraisSuivi.Visible = false;
            // 
            // buttonValiderSuivi
            // 
            this.buttonValiderSuivi.Location = new System.Drawing.Point(12, 106);
            this.buttonValiderSuivi.Name = "buttonValiderSuivi";
            this.buttonValiderSuivi.Size = new System.Drawing.Size(75, 23);
            this.buttonValiderSuivi.TabIndex = 9;
            this.buttonValiderSuivi.Text = "Valider";
            this.buttonValiderSuivi.UseVisualStyleBackColor = true;
            this.buttonValiderSuivi.Click += new System.EventHandler(this.buttonValiderSuivi_Click);
            // 
            // dateTimePickerSelectDateSuivi
            // 
            this.dateTimePickerSelectDateSuivi.CustomFormat = "";
            this.dateTimePickerSelectDateSuivi.Location = new System.Drawing.Point(196, 60);
            this.dateTimePickerSelectDateSuivi.Name = "dateTimePickerSelectDateSuivi";
            this.dateTimePickerSelectDateSuivi.Size = new System.Drawing.Size(200, 22);
            this.dateTimePickerSelectDateSuivi.TabIndex = 8;
            // 
            // labelSelectDateSuivi
            // 
            this.labelSelectDateSuivi.AutoSize = true;
            this.labelSelectDateSuivi.Location = new System.Drawing.Point(9, 65);
            this.labelSelectDateSuivi.Name = "labelSelectDateSuivi";
            this.labelSelectDateSuivi.Size = new System.Drawing.Size(151, 17);
            this.labelSelectDateSuivi.TabIndex = 7;
            this.labelSelectDateSuivi.Text = "Sélectionner une date:";
            // 
            // comboBoxSelectUserSuivi
            // 
            this.comboBoxSelectUserSuivi.FormattingEnabled = true;
            this.comboBoxSelectUserSuivi.Location = new System.Drawing.Point(196, 18);
            this.comboBoxSelectUserSuivi.Name = "comboBoxSelectUserSuivi";
            this.comboBoxSelectUserSuivi.Size = new System.Drawing.Size(152, 24);
            this.comboBoxSelectUserSuivi.TabIndex = 6;
            // 
            // labelSelectUserSuivi
            // 
            this.labelSelectUserSuivi.AutoSize = true;
            this.labelSelectUserSuivi.Location = new System.Drawing.Point(9, 21);
            this.labelSelectUserSuivi.Name = "labelSelectUserSuivi";
            this.labelSelectUserSuivi.Size = new System.Drawing.Size(176, 17);
            this.labelSelectUserSuivi.TabIndex = 5;
            this.labelSelectUserSuivi.Text = "Sélectionner un utilisateur:";
            // 
            // textBoxEtatSuivi
            // 
            this.textBoxEtatSuivi.Location = new System.Drawing.Point(71, 150);
            this.textBoxEtatSuivi.Name = "textBoxEtatSuivi";
            this.textBoxEtatSuivi.ReadOnly = true;
            this.textBoxEtatSuivi.Size = new System.Drawing.Size(200, 22);
            this.textBoxEtatSuivi.TabIndex = 17;
            this.textBoxEtatSuivi.Visible = false;
            // 
            // Comptable
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1006, 721);
            this.Controls.Add(this.buttonSuivi);
            this.Controls.Add(this.buttonView);
            this.Controls.Add(this.labelQuestion);
            this.Controls.Add(this.labelLogout);
            this.Controls.Add(this.buttonLogout);
            this.Controls.Add(this.panelSuivi);
            this.Controls.Add(this.panelView);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "Comptable";
            this.Text = "GSB - Comptable";
            this.Load += new System.EventHandler(this.Comptable_Load);
            this.panelView.ResumeLayout(false);
            this.panelView.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownModifyForfait)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewComptableNonForfaitise)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewComptableForfaitise)).EndInit();
            this.panelSuivi.ResumeLayout(false);
            this.panelSuivi.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewNonFraisSuivi)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewFraisSuivi)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button buttonSuivi;
        private System.Windows.Forms.Button buttonView;
        private System.Windows.Forms.Label labelQuestion;
        private System.Windows.Forms.Label labelLogout;
        private System.Windows.Forms.Button buttonLogout;
        private System.Windows.Forms.Panel panelView;
        private System.Windows.Forms.Panel panelSuivi;
        private System.Windows.Forms.ComboBox comboBoxSelectUser;
        private System.Windows.Forms.Label labelSelectUser;
        private System.Windows.Forms.Label labelSelectDate;
        private System.Windows.Forms.DateTimePicker dateTimePickerSelectDate;
        private System.Windows.Forms.Button buttonValidateSelect;
        private System.Windows.Forms.DataGridView dataGridViewComptableNonForfaitise;
        private System.Windows.Forms.Label labelNonForfaitiseViewComptable;
        private System.Windows.Forms.Label labelForfaitiseViewComptable;
        private System.Windows.Forms.DataGridView dataGridViewComptableForfaitise;
        private System.Windows.Forms.Button buttonModifyForfait;
        private System.Windows.Forms.NumericUpDown numericUpDownModifyForfait;
        private System.Windows.Forms.Label labelModifyForfait;
        private System.Windows.Forms.Label labelValidateComptableForfaitise;
        private System.Windows.Forms.Button buttonValidateComptableView;
        private System.Windows.Forms.Button buttonResetComptableView;
        private System.Windows.Forms.Button buttonValiderSuivi;
        private System.Windows.Forms.DateTimePicker dateTimePickerSelectDateSuivi;
        private System.Windows.Forms.Label labelSelectDateSuivi;
        private System.Windows.Forms.ComboBox comboBoxSelectUserSuivi;
        private System.Windows.Forms.Label labelSelectUserSuivi;
        private System.Windows.Forms.DataGridView dataGridViewNonFraisSuivi;
        private System.Windows.Forms.Label labelNonFraisSuivi;
        private System.Windows.Forms.Label labelFraisSuivi;
        private System.Windows.Forms.DataGridView dataGridViewFraisSuivi;
        private System.Windows.Forms.TextBox textBoxDateSuivi;
        private System.Windows.Forms.Label labelComboDateSuivi;
        private System.Windows.Forms.TextBox textBoxEtatSuivi;
        private System.Windows.Forms.Label labelComboEtatSuivi;
        private System.Windows.Forms.TextBox textBoxDate;
        private System.Windows.Forms.Label labelDate;
        private System.Windows.Forms.TextBox textBoxEtat;
        private System.Windows.Forms.Label labelEtat;
        private System.Windows.Forms.Button buttonSuiviRembourse;
        private System.Windows.Forms.Button buttonSuiviPaiement;
    }
}