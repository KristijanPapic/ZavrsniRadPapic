using Carer.Common;
using Carer.Model;
using Carer.Model.DomainModels;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Threading.Tasks;

namespace Carer.Repository
{
    class AppliedUserRepository : IAppliedUserRepository
    {
        private string connectionString = "Data Source=SQL8004.site4now.net;Initial Catalog=db_a8bd39_carerdb;User Id=db_a8bd39_carerdb_admin;Password=Micamaca12";

        public async Task<List<AppliedUserDomainModel>> GetAllAppliedUsersAsync(AppliedUserFilter filter)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"select UserId,UserName, PictureURl,OwnerOverallRating AS OwnerRating, CarerOverallRating AS CarerRating ,DateApplied from AppliedUser INNER JOIN [User] U on UserId = U.Id where AdId = '{filter.AdId}' AND Stat = 'pending' ";
            SqlCommand command = new SqlCommand(queryString, connection);
            SqlDataAdapter adapter = new SqlDataAdapter(command);
            DataSet auData = new DataSet();
            await Task.Run(() => (adapter.Fill(auData)));
            List<AppliedUserDomainModel> appliedUsers = new List<AppliedUserDomainModel>();
            if (auData.Tables[0].Rows.Count == 0)
            {
                return appliedUsers;
            }
            foreach (DataRow dataRow in auData.Tables[0].Rows)
            {
                appliedUsers.Add(new AppliedUserDomainModel
                {
                    UserId = Guid.Parse(Convert.ToString(dataRow["UserId"])),
                    UserName = Convert.ToString(dataRow["UserName"]),
                    PictureURL = Convert.ToString(dataRow["PictureURl"]),
                    Rating = filter.Type == 1 ? Convert.ToInt32(dataRow["CarerRating"]) : Convert.ToInt32(dataRow["OwnerRating"]),
                    DateApplied = Convert.ToDateTime(dataRow["DateApplied"]),
                });
            }
            return appliedUsers;
        }

        public async Task<SimpleAppliedUser> GetAppliedUserAsync(Guid adId)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"select UserId,UserName from AppliedUser INNER JOIN [User] U on UserId = U.Id where AdId = '{adId}' AND Stat = 'selected' ";
            SqlCommand command = new SqlCommand(queryString, connection);
            SqlDataAdapter adapter = new SqlDataAdapter(command);
            DataSet auData = new DataSet();
            await Task.Run(() => (adapter.Fill(auData)));
            SimpleAppliedUser appliedUser = new SimpleAppliedUser();
            if (auData.Tables[0].Rows.Count == 0)
            {
                return appliedUser;
            }
            DataRow userDataRow = auData.Tables[0].Rows[0];

            appliedUser.UserId = Guid.Parse(Convert.ToString(userDataRow["UserId"]));
            appliedUser.UserName = Convert.ToString(userDataRow["UserName"]);

            return appliedUser;
        }

        public async Task PostAppliedUserAsync(AppliedUserDomainModel appliedUser)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"INSERT INTO AppliedUser (UserId,AdId,Stat,DateApplied) VALUES ('{appliedUser.UserId}','{appliedUser.AdId}', 'pending', @DATEAPPLIED )";
            SqlCommand command = new SqlCommand(queryString, connection);
            command.Parameters.Add("@DATEAPPLIED", SqlDbType.DateTime);
            command.Parameters["@DATEAPPLIED"].Value = appliedUser.DateApplied;
            connection.Open();
            await command.ExecuteNonQueryAsync();
            connection.Close();
        }


        public async Task DeleteAppliedUserAsync(Guid userId, Guid adId)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"DELETE FROM AppliedUser where UserId = '{userId}' AND AdId = '{adId}' ";
            SqlCommand command = new SqlCommand(queryString, connection);
            connection.Open();
            await command.ExecuteNonQueryAsync();
            connection.Close();
        }

        public async Task PutAppliedUserAsync(Guid userId, Guid adId, string status)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"UPDATE AppliedUser SET Stat = '{status}' where UserId = '{userId}' AND AdId = '{adId}' ";
            SqlCommand command = new SqlCommand(queryString, connection);
            connection.Open();
            await command.ExecuteNonQueryAsync();
            if (status.Equals("selected"))
            {
                queryString = $"UPDATE AppliedUser SET Stat = 'declined' WHERE NOT UserId = '{userId}' AND AdId = '{adId}'";
                command.CommandText = queryString;
                await command.ExecuteNonQueryAsync();
            }

            connection.Close();
        }

        public async Task<int> GetAppliedUserCountAsync(Guid adId)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"SELECT COUNT(UserId) as count from AppliedUser where AdId = '{adId}' AND Stat = 'pending'";
            SqlCommand command = new SqlCommand(queryString, connection);
            SqlDataAdapter countAdapter = new SqlDataAdapter(command);
            DataSet countData = new DataSet();
            await Task.Run(() => (countAdapter.Fill(countData)));
            DataRow countDataRow = countData.Tables[0].Rows[0];
            int totalItemCount = Convert.ToInt32(countDataRow["count"]);
            return totalItemCount;
        }

        public async Task<string> GetAppliedUserStatusAsync(Guid userId, Guid adId)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"SELECT stat as Status from AppliedUser where UserId = '{userId}' AND AdId = '{adId}'";
            SqlCommand command = new SqlCommand(queryString, connection);
            SqlDataAdapter countAdapter = new SqlDataAdapter(command);
            DataSet statusData = new DataSet();
            await Task.Run(() => (countAdapter.Fill(statusData)));
            if (statusData.Tables[0].Rows.Count == 0)
            {
                return "";
            }
            DataRow statusDataRow = statusData.Tables[0].Rows[0];
            string status = Convert.ToString(statusDataRow["Status"]);
            return status;
        }




    }
}
