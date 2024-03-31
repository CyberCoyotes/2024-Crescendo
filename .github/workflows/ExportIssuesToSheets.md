name: Export GitHub Issues to Google Sheets

on:
  schedule:
    - cron: '0 0 * * *'  # Run every day at midnight

jobs:
  export-issues:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout Repository
        uses: actions/checkout@v2

      - name: Set up Python
        uses: actions/setup-python@v2
        with:
          python-version: 3.8

      - name: Install Dependencies
        run: |
          pip install -r requirements.txt

      - name: Run Export Script
        run: python export_issues.py
        env:
          GH_TOKEN: ${{ secrets.GITHUB_TOKEN }}
          GOOGLE_SHEETS_CREDENTIALS: ${{ secrets.GOOGLE_SHEETS_CREDENTIALS }}
